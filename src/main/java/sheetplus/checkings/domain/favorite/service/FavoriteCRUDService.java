package sheetplus.checkings.domain.favorite.service;


import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.config.AsyncConfig;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.event.repository.EventRepository;
import sheetplus.checkings.domain.favorite.dto.request.FavoriteRequestDto;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteCreateResponseDto;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteResponseDto;
import sheetplus.checkings.domain.favorite.entity.Favorite;
import sheetplus.checkings.domain.favorite.repository.FavoriteRepository;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.JwtUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static sheetplus.checkings.exception.error.ApiError.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteCRUDService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final ContestRepository contestRepository;
    private final EventRepository eventRepository;
    private final JwtUtil jwtUtil;
    private final AsyncConfig asyncConfig;

    @Transactional
    public FavoriteCreateResponseDto createFavorite(String token, FavoriteRequestDto favoriteRequestDto){
        Member member = memberRepository
                .findById(jwtUtil.getMemberId(token))
                .orElseThrow(()-> new ApiException(MEMBER_NOT_FOUND));
        Contest contest = contestRepository
                .findById(favoriteRequestDto.getContestId())
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        Event event = eventRepository
                .findById(favoriteRequestDto.getEventId())
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));

        if(!favoriteRepository.findByFavoriteMember_IdAndFavoriteContest_IdAndFavoriteEvent_Id(
                member.getId(), contest.getId(), event.getId()
        ).isEmpty()){
            throw new ApiException(FAVORITE_EXISTS);
        }

        Favorite favorite = Favorite.builder()
                .favoriteMember(member)
                .favoriteContest(contest)
                .favoriteEvent(event)
                .build();
        favorite.setMemberFavorite(member);
        favoriteRepository.save(favorite);
        subscribeTopics(favoriteRequestDto, event, contest);


        return FavoriteCreateResponseDto.builder()
                .favoriteId(favorite.getId())
                .studentId(member.getStudentId())
                .contestName(contest.getName())
                .eventName(event.getName())
                .build();
    }

    @Retryable(
            retryFor = {ExecutionException.class, InterruptedException.class},
            maxAttempts = 5,
            backoff = @Backoff(
                    random = true,
                    delay = 10000,
                    multiplier = 3,
                    maxDelay = 600000
            )
    )
    public void subscribeTopics(FavoriteRequestDto favoriteRequestDto, Event event, Contest contest) {
        ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
                .getInstance()
                .subscribeToTopicAsync(Collections.singletonList(favoriteRequestDto.getDeviceToken())
                        ,(event.getId().toString()+ "-" + contest.getId().toString()));
        // FCM 구독 등록 로직
        apiFuture.addListener(() ->{
            try{
                TopicManagementResponse response = apiFuture.get();
                log.info("토픽 구독 성공: {}", response.getSuccessCount());
            } catch (ExecutionException | InterruptedException e) {
                log.error("토픽 구독 관련 예외 발생: {}", e.getMessage());
                throw new RuntimeException();
            }
        }, asyncConfig.getSubscribeFcmExecutor());
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponseDto> getFavorites(String token, Long contestId) {
        ArrayList<Favorite> favorites
                = favoriteRepository
                .findByFavoriteMember_IdAndFavoriteContest_Id(jwtUtil.getMemberId(token), contestId);

        return favorites.stream()
                .map(p -> FavoriteResponseDto.builder()
                        .favoriteId(p.getId())
                        .eventName(p.getFavoriteEvent().getName())
                        .startTime(p.getFavoriteEvent().getStartTime())
                        .endTime(p.getFavoriteContest().getEndDate())
                        .location(p.getFavoriteEvent().getLocation())
                        .building(p.getFavoriteEvent().getBuilding())
                        .speakerName(p.getFavoriteEvent().getSpeakerName())
                        .major(p.getFavoriteEvent().getSpeakerName())
                        .major(p.getFavoriteEvent().getSpeakerName())
                        .conditionMessage(p.getFavoriteEvent().getEventCondition().getMessage())
                        .eventTypeMessage(p.getFavoriteEvent().getEventType().getMessage())
                        .categoryMessage(p.getFavoriteEvent().getEventCategory().getMessage())
                        .build())
                .toList();
    }


    @Transactional
    public void deleteFavorites(Long favoriteId, String token, String deviceToken){
        Member member = memberRepository.findById(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new ApiException(FAVORITE_NOT_FOUND));

        if(favorite.getFavoriteMember().getId().equals(member.getId())){
            unSubscribeTopics(deviceToken, favorite);

            favoriteRepository.deleteById(favoriteId);
        }
    }

    @Retryable(
            retryFor = {ExecutionException.class, InterruptedException.class},
            maxAttempts = 5,
            backoff = @Backoff(
                    random = true,
                    delay = 10000,
                    multiplier = 3,
                    maxDelay = 600000
            )
    )
    private void unSubscribeTopics(String deviceToken, Favorite favorite) {
        ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
                .getInstance()
                .unsubscribeFromTopicAsync(Collections.singletonList(deviceToken)
                        ,(favorite.getFavoriteEvent().getId().toString()
                                + "-" + favorite.getFavoriteContest().getId().toString()));

        // 토픽 구독취소
        apiFuture.addListener(()->{
            try{
                TopicManagementResponse response = apiFuture.get();
                log.info("토픽 구독취소 성공: {}", response.getSuccessCount());
            }catch (ExecutionException | InterruptedException e) {
                log.error("토픽 구독취소 관련 예외 발생: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }, asyncConfig.getSubscribeFcmExecutor());
    }

}
