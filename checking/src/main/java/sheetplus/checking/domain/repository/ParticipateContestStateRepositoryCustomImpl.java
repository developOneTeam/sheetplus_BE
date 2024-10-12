package sheetplus.checking.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sheetplus.checking.domain.entity.enums.MeritType;

import static sheetplus.checking.domain.entity.QParticipateContestState.participateContestState;

@RequiredArgsConstructor
@Slf4j
public class ParticipateContestStateRepositoryCustomImpl implements ParticipateContestStateRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public void targetUpdates(int condition) {
        queryFactory
                .update(participateContestState)
                .set(participateContestState.meritType, MeritType.TARGET)
                .where(participateContestState.eventsCount.goe(condition))
                .execute();
    }
}
