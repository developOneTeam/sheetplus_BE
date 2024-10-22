package sheetplus.checkings.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sheetplus.checkings.domain.dto.MemberInfoDto;
import sheetplus.checkings.domain.dto.ParticipateInfoDto;
import sheetplus.checkings.domain.entity.Member;
import sheetplus.checkings.domain.entity.enums.MeritType;

import java.util.List;

import static sheetplus.checkings.domain.entity.QParticipateContest.participateContest;


@RequiredArgsConstructor
@Slf4j
public class ParticipateContestStateRepositoryCustomImpl implements ParticipateContestStateRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public void targetUpdates(int condition) {
        queryFactory
                .update(participateContest)
                .set(participateContest.meritType, MeritType.PRIZE_TARGET)
                .where(participateContest.eventsCount.goe(condition))
                .execute();
    }

    @Override
    public ParticipateInfoDto participateContestCounts(Long id) {
        Long countOne = queryFactory
                .select(participateContest.count())
                .from(participateContest)
                .where(participateContest.eventsCount.goe(1))
                .fetchOne();
        Long countAll = queryFactory
                .select(participateContest.count())
                .from(participateContest)
                .where(participateContest.eventsCount.goe(5))
                .fetchOne();

        return ParticipateInfoDto.builder()
                .completeEventMemberCounts(countAll)
                .moreThanOneCounts(countOne)
                .moreThanFiveCounts(countAll)
                .build();
    }


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    @Override
    public List<MemberInfoDto> participateMemberInfoRead(Long contestId) {
        List<Member> members = queryFactory
                .select(participateContest.memberParticipateContestState)
                .from(participateContest)
                .where(participateContest.contestParticipateContestState.id.eq(contestId).and(
                        participateContest.eventsCount.goe(1)
                ))
                .fetch();

        return members.stream()
                .map(p -> MemberInfoDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .email(p.getUniversityEmail())
                        .major(p.getMajor())
                        .memberType(p.getMemberType())
                        .studentId(p.getStudentId())
                        .build())
                .toList();
    }

    @Override
    public List<MemberInfoDto> drawMemberInfoRead(Long contestId) {
        List<Member> members = queryFactory
                .select(participateContest.memberParticipateContestState)
                .from(participateContest)
                .where(participateContest.contestParticipateContestState.id.eq(contestId).and(
                        participateContest.eventsCount.goe(5)
                ))
                .fetch();

        return members.stream()
                .map(p -> MemberInfoDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .email(p.getUniversityEmail())
                        .major(p.getMajor())
                        .memberType(p.getMemberType())
                        .studentId(p.getStudentId())
                        .build())
                .toList();
    }
}
