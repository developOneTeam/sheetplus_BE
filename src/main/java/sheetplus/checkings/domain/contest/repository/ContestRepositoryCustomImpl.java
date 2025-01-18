package sheetplus.checkings.domain.contest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.AdminEventStatsDto;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.ContestInfoWithCounts;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryExceptLinksResponseDto;
import sheetplus.checkings.domain.enums.ContestCons;
import sheetplus.checkings.domain.enums.EntryType;
import sheetplus.checkings.domain.enums.EventType;
import sheetplus.checkings.domain.event.dto.EventDto.EventExceptLinksResponseDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.enums.EventCategory;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static sheetplus.checkings.domain.contest.entity.QContest.contest;
import static sheetplus.checkings.domain.entry.entity.QEntry.entry;
import static sheetplus.checkings.domain.event.entity.QEvent.event;
import static sheetplus.checkings.domain.participatecontest.entity.QParticipateContest.participateContest;
import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
public class ContestRepositoryCustomImpl implements ContestRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<EventResponseDto> findTodayEvents(Long contestId, Pageable pageable) {
        Contest findContest = queryFactory.selectFrom(contest)
                .join(contest.participateContestStateContest, participateContest)
                .fetchJoin()
                .where(contest.id.eq(contestId))
                .fetchFirst();

        if(findContest == null){
            throw new ApiException(CONTEST_NOT_FOUND);
        }

        List<Event> events = queryFactory.selectFrom(event)
                .where(event.eventContest.id.eq(findContest.getId())
                        .and(event.startTime.dayOfMonth().eq(LocalDateTime.now().getDayOfMonth())
                                .or(event.endTime.dayOfMonth().eq(LocalDateTime.now().getDayOfMonth())))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return getEventResponseDtos(events);
    }

    private List<EventResponseDto> getEventResponseDtos(List<Event> events) {
        return events.stream()
                .map(p -> EventResponseDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .major(p.getMajor())
                        .eventTypeMessage(p.getEventType().getMessage())
                        .conditionMessage(p.getEventCondition().getMessage())
                        .location(p.getLocation())
                        .building(p.getBuilding())
                        .categoryMessage(p.getEventCategory().getMessage())
                        .speakerName(p.getSpeakerName())
                        .startTime(p.getStartTime())
                        .endTime(p.getEndTime())
                        .build())
                .toList();
    }

    @Override
    public List<EventResponseDto> findNowAfterEvents(Long contestId) {
        Contest findContest = queryFactory.selectFrom(contest)
                .where(contest.id.eq(contestId))
                .fetchFirst();

        if(findContest == null){
            throw new ApiException(CONTEST_NOT_FOUND);
        }

        List<Event> events = queryFactory.selectFrom(event)
                .where(event.eventContest.id.eq(findContest.getId())
                        .and(event.startTime.after(LocalDateTime.now())
                                .or(event.endTime.after(LocalDateTime.now())))
                        )
                .fetch();

        return getEventResponseDtos(events);
    }

    @Override
    public List<EventResponseDto> findParticipateEvents(Long contestId, List<EventCategory> eventCategories) {
        Contest findContest = queryFactory.selectFrom(contest)
                .where(contest.id.eq(contestId))
                .fetchFirst();
        if(findContest == null){
            throw new ApiException(CONTEST_NOT_FOUND);
        }


        List<Event> events = new ArrayList<>();

        for (EventCategory category : eventCategories) {
            events.add(queryFactory.selectFrom(event)
                    .where(event.eventContest.id.eq(findContest.getId())
                            .and(event.eventCategory.eq(category)))
                    .fetchFirst());
        }

        return getEventResponseDtos(events);
    }

    @Override
    public List<ContestInfoWithCounts> findContestInfoWithCounts() {
        return queryFactory.selectFrom(contest)
                .leftJoin(contest.participateContestStateContest, participateContest)
                .fetchJoin()
                .fetch().stream()
                .map(p -> ContestInfoWithCounts.builder()
                        .name(p.getName())
                        .startDate(p.getStartDate())
                        .endDate(p.getEndDate())
                        .cons(p.getCons())
                        .eventCounts(p.getEvents().size())
                        .entryCounts(p.getEntries().size())
                        .build())
                .toList();
    }

    @Override
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
                        entry.entryType
                                .when(EntryType.PRELIMINARY).then(EntryType.FINALS.getMessage())
                                .when(EntryType.FINALS).then(EntryType.PRELIMINARY.getMessage())
                                .otherwise("Unknown Type")
                                .as("entryType")
                ))
                .from(contest)
                .innerJoin(entry)
                .on(entry.entryContest.id.eq(contestId))
                .where(contest.id.eq(contestId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
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
                                event.eventCondition
                                        .when(ContestCons.EVENT_PROGRESS)
                                        .then(ContestCons.EVENT_PROGRESS.getMessage())
                                        .when(ContestCons.EVENT_BEFORE)
                                        .then(ContestCons.EVENT_BEFORE.getMessage())
                                        .when(ContestCons.EVENT_FINISH)
                                        .then(ContestCons.EVENT_FINISH.getMessage())
                                        .otherwise("Unknown Condition")
                                        .as("conditionMessage"),
                                event.eventType
                                        .when(EventType.NO_CHECKING)
                                        .then(EventType.NO_CHECKING.getMessage())
                                        .when(EventType.CHECKING)
                                        .then(EventType.CHECKING.getMessage())
                                        .otherwise("Unknown Type")
                                        .as("eventTypeMessage"),
                                event.eventCategory
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
                                        .as("categoryMessage")
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
}
