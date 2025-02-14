package sheetplus.checkings.domain.contest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.*;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestInfoResponseDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryExceptLinksResponseDto;
import sheetplus.checkings.domain.enums.ContestCons;
import sheetplus.checkings.domain.enums.EntryType;
import sheetplus.checkings.domain.enums.EventType;
import sheetplus.checkings.domain.event.dto.EventDto.EventExceptLinksResponseDto;
import sheetplus.checkings.domain.enums.EventCategory;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import java.time.LocalDateTime;
import java.util.List;

import static sheetplus.checkings.domain.contest.entity.QContest.contest;
import static sheetplus.checkings.domain.entry.entity.QEntry.entry;
import static sheetplus.checkings.domain.event.entity.QEvent.event;
import static sheetplus.checkings.domain.participatecontest.entity.QParticipateContest.participateContest;
import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ContestQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<EventExceptLinksResponseDto> findTodayEvents(Long contestId, Pageable pageable) {
        return queryFactory
                .select(
                        Projections.constructor(
                                EventExceptLinksResponseDto.class,
                                event.id,
                                event.name,
                                event.startTime,
                                event.endTime,
                                event.location,
                                event.building,
                                event.speakerName,
                                event.major,
                                getEventCondition(),
                                getEventTypeMessage(),
                                getEventCategory()
                        )
                ).distinct()
                .from(contest)
                .innerJoin(event)
                .on(event.eventContest.id.eq(contestId))
                .where(event.startTime.dayOfMonth().loe(LocalDateTime.now().getDayOfMonth())
                        .and(event.endTime.dayOfMonth().goe(LocalDateTime.now().getDayOfMonth()))
                        .and(event.startTime.before(LocalDateTime.now()))
                        .and(event.endTime.after(LocalDateTime.now()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    

    
    public List<EventExceptLinksResponseDto> findNowAfterEvents(Long contestId, String building) {
        return queryFactory.select(
                        Projections.constructor(
                                EventExceptLinksResponseDto.class,
                                event.id,
                                event.name,
                                event.startTime,
                                event.endTime,
                                event.location,
                                event.building,
                                event.speakerName,
                                event.major,
                                getEventCondition(),
                                getEventTypeMessage(),
                                getEventCategory()
                        )
                ).distinct()
                .from(event)
                .innerJoin(contest)
                .on(event.eventContest.id.eq(contestId))
                .where(event.eventContest.id.eq(contestId)
                        .and(event.startTime.after(LocalDateTime.now())
                                .or(event.endTime.after(LocalDateTime.now())))
                        .and(event.building.eq(building))
                )
                .fetch();
    }

    
    public List<EventExceptLinksResponseDto> findParticipateEvents(Long participateId) {
        return queryFactory
                .select(Projections.constructor(
                                        EventExceptLinksResponseDto.class,
                                        event.id,
                                        event.name,
                                        event.startTime,
                                        event.endTime,
                                        event.location,
                                        event.building,
                                        event.speakerName,
                                        event.major,
                                getEventCondition(),
                                getEventTypeMessage(),
                                getEventCategory()
                                )
                        )
                .distinct()
                .from(event)
                .innerJoin(participateContest)
                .on(event.eventParticipateContest.id.eq(participateId))
                .fetch();
    }

    
    public List<ContestInfoWithCounts> findContestInfoWithCounts() {
        return queryFactory.selectFrom(contest)
                .leftJoin(contest.participateContestStateContest, participateContest)
                .fetchJoin()
                .fetch().stream()
                .map(p -> ContestInfoWithCounts.builder()
                        .name(p.getName())
                        .startDate(p.getStartDate())
                        .endDate(p.getEndDate())
                        .cons(p.getCons().getMessage())
                        .eventCounts(p.getEvents().size())
                        .entryCounts(p.getEntries().size())
                        .build())
                .toList();
    }


    public List<EntryExceptLinksResponseDto> findContestWithEntries(Long contestId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(
                        EntryExceptLinksResponseDto.class,
                        entry.id.as("id"),
                        entry.name.as("name"),
                        entry.location.as("location"),
                        entry.building.as("building"),
                        entry.teamNumber.as("teamNumber"),
                        entry.major.as("major"),
                        entry.professorName.as("professorName"),
                        entry.leaderName.as("leaderName"),
                        getEntryType()
                ))
                .distinct()
                .from(contest)
                .innerJoin(entry)
                .on(entry.entryContest.id.eq(contestId))
                .where(contest.id.eq(contestId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    public AdminEventStatsDto findContestWithEvents(Long contestId, Pageable pageable) {
        List<EventExceptLinksResponseDto> events = queryFactory.select(
                Projections.constructor(
                                EventExceptLinksResponseDto.class,
                                event.id,
                                event.name,
                                event.startTime,
                                event.endTime,
                                event.location,
                                event.building,
                                event.speakerName,
                                event.major,
                        getEventCondition(),
                        getEventTypeMessage(),
                        getEventCategory()
                        )
                )
                .from(contest)
                .innerJoin(event)
                .on(event.eventContest.id.eq(contestId))
                .where(contest.id.eq(contestId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return AdminEventStatsDto
                .builder()
                .eventCounts(events.size())
                .allEvents(events)
                .build();
    }

    
    public AdminContestStatsDto findContestStats(Long contestId) {

        AdminContestStatsDto adminContestStatsDto
                = queryFactory.select(
                        Projections.constructor(
                                AdminContestStatsDto.class,
                                contest.name.coalesce("Contest 이름 데이터가 없습니다.").as("contestName"),
                                contest.startDate.coalesce(LocalDateTime.now()).as("contestStart"),
                                contest.endDate.coalesce(LocalDateTime.now()).as("contestEnd"),
                                event.location.min().coalesce("장소 없음").as("locationName"),
                                event.location.countDistinct().coalesce(0L).as("locationCounts"),
                                countRemainEvents(contestId),
                                countFinishEvents(contestId),
                                countNotTodayEvents(contestId),
                                entry.major.countDistinct().coalesce(0L)
                                        .as("entryMajorCounts"),
                                entry.countDistinct().coalesce(0L).as("entryCounts"),
                                countPreliminaryEntry(contestId),
                                countFinalEntry(contestId)
                        )
                )
                .from(contest)
                .leftJoin(event)
                .on(event.eventContest.id.eq(contestId))
                .leftJoin(entry)
                .on(entry.entryContest.id.eq(contestId))
                .where(contest.id.eq(contestId))
                .groupBy(contest)
                .fetchFirst();

        if(adminContestStatsDto == null){
            throw new ApiException(CONTEST_NOT_FOUND);
        }

        return adminContestStatsDto;
    }




    public List<ContestInfoResponseDto> findAllContestInfo(Pageable pageable) {
        return queryFactory.
                select(
                        Projections.constructor(
                                ContestInfoResponseDto.class,
                                contest.id.as("contestId"),
                                contest.name.as("contestName")
                        )
                ).from(contest)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }



    private static StringExpression getEntryType() {
        return entry.entryType
                .when(EntryType.PRELIMINARY).then(EntryType.FINALS.getMessage())
                .when(EntryType.FINALS).then(EntryType.PRELIMINARY.getMessage())
                .otherwise("Unknown Type")
                .as("entryType");
    }

    private static StringExpression getEventCondition() {
        return event.eventCondition
                .when(ContestCons.EVENT_PROGRESS)
                .then(ContestCons.EVENT_PROGRESS.getMessage())
                .when(ContestCons.EVENT_BEFORE)
                .then(ContestCons.EVENT_BEFORE.getMessage())
                .when(ContestCons.EVENT_FINISH)
                .then(ContestCons.EVENT_FINISH.getMessage())
                .otherwise("Unknown Condition")
                .as("conditionMessage");
    }

    private static StringExpression getEventTypeMessage() {
        return event.eventType
                .when(EventType.NO_CHECKING)
                .then(EventType.NO_CHECKING.getMessage())
                .when(EventType.CHECKING)
                .then(EventType.CHECKING.getMessage())
                .otherwise("Unknown Type")
                .as("eventTypeMessage");
    }

    private static StringExpression getEventCategory() {
        return event.eventCategory
                .when(EventCategory.EVENT_ONE)
                .then(EventCategory.EVENT_ONE.getMessage())
                .when(EventCategory.EVENT_TWO)
                .then(EventCategory.EVENT_TWO.getMessage())
                .when(EventCategory.EVENT_THREE)
                .then(EventCategory.EVENT_THREE.getMessage())
                .when(EventCategory.EVENT_FOUR)
                .then(EventCategory.EVENT_FOUR.getMessage())
                .when(EventCategory.EVENT_FIVE)
                .then(EventCategory.EVENT_FIVE.getMessage())
                .otherwise("unknown Category")
                .as("categoryMessage");
    }

    private static JPQLQuery<Long> countFinalEntry(Long contestId) {
        return JPAExpressions
                .select(entry.countDistinct().coalesce(0L).as("entryFinalCounts"))
                .from(entry).innerJoin(contest)
                .on(entry.entryContest.id.eq(contestId))
                .where(entry.entryType.eq(EntryType.FINALS));
    }

    private static JPQLQuery<Long> countPreliminaryEntry(Long contestId) {
        return JPAExpressions
                .select(entry.countDistinct().coalesce(0L).as("entryPreliminaryCounts"))
                .from(entry).innerJoin(contest)
                .on(entry.entryContest.id.eq(contestId))
                .where(entry.entryType.eq(EntryType.PRELIMINARY));
    }

    private static JPQLQuery<Long> countNotTodayEvents(Long contestId) {
        return JPAExpressions
                .select(event.countDistinct().coalesce(0L).as("notTodayEvents"))
                .from(event).innerJoin(contest)
                .on(event.eventContest.id.eq(contestId))
                .where(event.startTime.dayOfMonth().gt(LocalDateTime.now().getDayOfMonth())
                        .or(event.endTime.dayOfMonth().lt(LocalDateTime.now().getDayOfMonth()))
                );
    }

    private static JPQLQuery<Long> countFinishEvents(Long contestId) {
        return JPAExpressions
                .select(event.countDistinct().coalesce(0L).as("finishEvents"))
                .from(event).innerJoin(contest)
                .on(event.eventContest.id.eq(contestId))
                .where(event.endTime.before(LocalDateTime.now())
                        .and(event.startTime.dayOfMonth().loe(LocalDateTime.now().getDayOfMonth()))
                        .and(event.endTime.dayOfMonth().goe(LocalDateTime.now().getDayOfMonth()))
                );
    }

    private static JPQLQuery<Long> countRemainEvents(Long contestId) {
        return JPAExpressions
                .select(event.countDistinct().coalesce(0L).as("remainEvents"))
                .from(event).innerJoin(contest)
                .on(event.eventContest.id.eq(contestId))
                .where(event.startTime.dayOfMonth().loe(LocalDateTime.now().getDayOfMonth())
                        .and(event.endTime.dayOfMonth().goe(LocalDateTime.now().getDayOfMonth()))
                        .and(event.startTime.before(LocalDateTime.now())
                                .and(event.endTime.after(LocalDateTime.now())))
                );
    }
}
