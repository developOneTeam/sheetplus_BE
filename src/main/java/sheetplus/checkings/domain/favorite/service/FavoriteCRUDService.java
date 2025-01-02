package sheetplus.checkings.domain.favorite.service;


import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.hateoas.Link;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.page.student.controller.StudentPageController;
import sheetplus.checkings.config.AsyncConfig;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.enums.SubscribeStatus;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.event.repository.EventRepository;
import sheetplus.checkings.domain.favorite.controller.FavoriteController;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteRequestDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteCreateResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.SubScribeResponseDTO;
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
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
    private final StringRedisTemplate redisTemplate;

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
        Long favoriteId = favoriteRepository.save(favorite).getId();
        subscribeTopics(favoriteRequestDto, event, contest, favoriteId.toString());

        List<Link> chainListLink = new ArrayList<>();
        chainListLink.add(linkTo(methodOn(FavoriteController.class)
                .getFavorites(token, contest.getId(),
                        0, 1)).withRel("즐겨찾기 조회"));
        chainListLink.add(linkTo(methodOn(StudentPageController.class)
                .readStudentActivities(token,contest.getId())).withRel("학생 활동 페이지"));
        chainListLink.add(linkTo(methodOn(FavoriteController.class)
                .deleteFavorite(token, contest.getId(), "deviceToken"))
                .withRel("즐겨찾기 삭제"));
        chainListLink.add(linkTo(methodOn(FavoriteController.class)
                .favoriteStatusCheck(favorite.getId(), "subscribe"))
                .withRel("구독등록 상태 확인"));


        return FavoriteCreateResponseDto.builder()
                .favoriteId(favorite.getId())
                .studentId(member.getStudentId())
                .contestName(contest.getName())
                .eventName(event.getName())
                .links(chainListLink)
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
    public void subscribeTopics(FavoriteRequestDto favoriteRequestDto, Event event, Contest contest, String favoriteId) {
        redisTemplate.opsForValue()
                .set(favoriteId+"_subscribe", SubscribeStatus.PENDING.getStatus(), 300, TimeUnit.SECONDS);

        ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
                .getInstance()
                .subscribeToTopicAsync(Collections.singletonList(favoriteRequestDto.getDeviceToken())
                        ,(event.getId().toString()+ "-" + contest.getId().toString()));
        // FCM 구독 등록 로직
        apiFuture.addListener(() ->{
            try{
                TopicManagementResponse response = apiFuture.get();
                log.info("토픽 구독 성공: {}", response.getSuccessCount());

                if(response.getFailureCount() > 0){
                    log.info("토픽 구독 실패: {}", response.getFailureCount());
                    redisTemplate.opsForValue()
                            .set(favoriteId+"_subscribe", SubscribeStatus.FAILED.getStatus(), 600, TimeUnit.SECONDS);
                    favoriteRepository.deleteById(Long.parseLong(favoriteId));
                    return;
                }

                redisTemplate.opsForValue()
                        .set(favoriteId+"_subscribe", SubscribeStatus.SUCCESS.getStatus(), 600, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException e) {
                log.error("토픽 구독 관련 예외 발생: {}", e.getMessage());
                redisTemplate.opsForValue()
                        .set(favoriteId+"_subscribe", SubscribeStatus.FAILED.getStatus(), 600, TimeUnit.SECONDS);
                favoriteRepository.deleteById(Long.parseLong(favoriteId));
                throw new RuntimeException();
            }
        }, asyncConfig.getSubscribeFcmExecutor());
    }

    public SubScribeResponseDTO getFavoriteStatus(Long id, String type){
        String status = redisTemplate.opsForValue().get(id.toString() + "_"+type);
        if(status == null){
            throw new ApiException(SUBSCRIBE_STATUS_NOT_FOUND);
        }

        if(status.equals(SubscribeStatus.FAILED.getStatus())){
            return SubScribeResponseDTO.builder()
                    .statusType(SubscribeStatus.FAILED.getStatus())
                    .statusMessage(SubscribeStatus.FAILED.getMessage())
                    .build();
        }

        if(status.equals(SubscribeStatus.SUCCESS.getStatus())){
            return SubScribeResponseDTO.builder()
                    .statusType(SubscribeStatus.SUCCESS.getStatus())
                    .statusMessage(SubscribeStatus.SUCCESS.getMessage())
                    .build();
        }

        return SubScribeResponseDTO.builder()
                .statusType(SubscribeStatus.PENDING.getStatus())
                .statusMessage(SubscribeStatus.PENDING.getMessage())
                .build();
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponseDto> getFavorites(String token, Long contestId, Pageable pageable) {
        List<Favorite> favorites
                = favoriteRepository
                .findAllByFavoriteMember_IdAndFavoriteContest_Id(
                        jwtUtil.getMemberId(token), contestId, pageable)
                .getContent();

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
        redisTemplate.opsForValue()
                .set(favorite.getId()+"_unsubscribe", SubscribeStatus.PENDING.getStatus(), 300, TimeUnit.SECONDS);
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

                if(response.getFailureCount() > 0){
                    log.info("토픽 구독취소 실패: {}", response.getFailureCount());
                    redisTemplate.opsForValue()
                            .set(favorite.getId()+"_unsubscribe", SubscribeStatus.FAILED.getStatus(), 600, TimeUnit.SECONDS);
                    favoriteRepository.deleteById(Long.parseLong(favorite.getId()+"_unsubscribe"));
                    return;
                }

                redisTemplate.opsForValue()
                        .set(favorite.getId()+"_unsubscribe", SubscribeStatus.SUCCESS.getStatus(), 600, TimeUnit.SECONDS);
                favoriteRepository.deleteById(favorite.getId());
            }catch (ExecutionException | InterruptedException e) {
                log.error("토픽 구독취소 관련 예외 발생: {}", e.getMessage());
                redisTemplate.opsForValue()
                        .set(favorite.getId()+"_unsubscribe", SubscribeStatus.FAILED.getStatus(), 600, TimeUnit.SECONDS);
                throw new RuntimeException(e);
            }
        }, asyncConfig.getSubscribeFcmExecutor());
    }

}
