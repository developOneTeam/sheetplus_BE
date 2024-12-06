package sheetplus.checkings.domain.favorite.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.List;

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

    @Transactional
    public FavoriteCreateResponseDto createFavorite(String token, FavoriteRequestDto favoriteRequestDto) {
        Member member = memberRepository
                .findById(jwtUtil.getMemberId(token))
                .orElseThrow(()-> new ApiException(MEMBER_NOT_FOUND));
        Contest contest = contestRepository
                .findById(favoriteRequestDto.getContestId())
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        Event event = eventRepository
                .findById(favoriteRequestDto.getEventId())
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));

        Favorite favorite = Favorite.builder()
                .favoriteMember(member)
                .favoriteContest(contest)
                .favoriteEvent(event)
                .build();

        favoriteRepository.save(favorite);

        return FavoriteCreateResponseDto.builder()
                .favoriteId(favorite.getId())
                .studentId(member.getStudentId())
                .contestName(contest.getName())
                .eventName(event.getName())
                .build();
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
    public void deleteFavorites(Long favoriteId, String token){
        Member member = memberRepository.findById(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new ApiException(FAVORITE_NOT_FOUND));

        if(favorite.getFavoriteMember().getId().equals(member.getId())){
            favoriteRepository.deleteById(favoriteId);
        }
    }

}
