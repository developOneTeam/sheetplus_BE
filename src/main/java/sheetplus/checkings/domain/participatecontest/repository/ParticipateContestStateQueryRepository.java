package sheetplus.checkings.domain.participatecontest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.domain.participatecontest.dto.ParticipateContestDto.ParticipateInfoResponseDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.enums.MeritType;

import java.util.List;

import static sheetplus.checkings.domain.participatecontest.entity.QParticipateContest.participateContest;


@RequiredArgsConstructor
@Slf4j
@Repository
public class ParticipateContestStateQueryRepository{

    private final JPAQueryFactory queryFactory;

    
    public void targetUpdates(int condition) {
        queryFactory
                .update(participateContest)
                .set(participateContest.meritType, MeritType.PRIZE_TARGET)
                .where(participateContest.eventsCount.goe(condition))
                .execute();
    }

    
    public ParticipateInfoResponseDto participateContestCounts(Long contestId) {
        ParticipateInfoResponseDto participateInfoResponseDto = queryFactory
                .select(
                        Projections.fields(
                                ParticipateInfoResponseDto.class,
                                new CaseBuilder()
                                        .when(participateContest.eventsCount.goe(1))
                                        .then(participateContest.count().coalesce(0L))
                                        .otherwise(0L).coalesce(0L).as("moreThanOneCounts"),
                                new CaseBuilder()
                                        .when(participateContest.eventsCount.goe(5))
                                        .then(participateContest.count().coalesce(0L))
                                        .otherwise(0L).coalesce(0L).as("moreThanFiveCounts")
                        )
                ).from(participateContest)
                .where(participateContest.contestParticipateContestState.id.eq(contestId))
                .groupBy(participateContest.eventsCount)
                .fetchFirst();
        if(participateInfoResponseDto == null){
            participateInfoResponseDto = ParticipateInfoResponseDto.builder()
                    .moreThanOneCounts(0L)
                    .moreThanFiveCounts(0L)
                    .build();
        }

        return participateInfoResponseDto;
    }


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    
    public List<MemberInfoResponseDto> participateMemberInfoRead(Long contestId) {
        List<Member> members = queryFactory
                .select(participateContest.memberParticipateContestState)
                .from(participateContest)
                .where(participateContest.contestParticipateContestState.id.eq(contestId).and(
                        participateContest.eventsCount.goe(1)
                ))
                .fetch();

        return members.stream()
                .map(p -> MemberInfoResponseDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .email(p.getUniversityEmail())
                        .major(p.getMajor())
                        .memberType(p.getMemberType())
                        .studentId(p.getStudentId())
                        .build())
                .toList();
    }

    
    public List<MemberInfoResponseDto> drawMemberInfoRead(Long contestId, Pageable pageable) {
        List<Member> members = queryFactory
                .select(participateContest.memberParticipateContestState)
                .from(participateContest)
                .where(participateContest.contestParticipateContestState.id.eq(contestId).and(
                        participateContest.eventsCount.goe(5)
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return members.stream()
                .map(p -> MemberInfoResponseDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .email(p.getUniversityEmail())
                        .major(p.getMajor())
                        .memberType(p.getMemberType())
                        .studentId(p.getStudentId())
                        .build())
                .toList();
    }

    
    public Integer participateCounts(Long memberId, Long contestId) {
        return queryFactory.select(participateContest.eventsCount)
                .from(participateContest)
                .where(participateContest.memberParticipateContestState.id.eq(memberId)
                        .and(participateContest.contestParticipateContestState.id.eq(contestId)))
                .fetchFirst();
    }
}
