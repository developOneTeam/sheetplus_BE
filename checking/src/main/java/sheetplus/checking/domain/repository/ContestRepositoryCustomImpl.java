package sheetplus.checking.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sheetplus.checking.domain.dto.EventResponseDto;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Event;
import sheetplus.checking.domain.entity.enums.EventCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static sheetplus.checking.domain.entity.QContest.contest;
import static sheetplus.checking.domain.entity.QEvent.event;

@Slf4j
@RequiredArgsConstructor
public class ContestRepositoryCustomImpl implements ContestRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<EventResponseDto> findTodayEvents(Long contestId) {
        Contest findContest = queryFactory.selectFrom(contest)
                .where(contest.id.eq(contestId))
                .fetchFirst();

        List<Event> events = queryFactory.selectFrom(event)
                .where(event.eventContest.id.eq(findContest.getId())
                        .and(event.startTime.dayOfMonth().eq(LocalDateTime.now().getDayOfMonth())
                                .or(event.endTime.dayOfMonth().eq(LocalDateTime.now().getDayOfMonth())))
                )
                .fetch();

        return getEventResponseDtos(events);
    }

    private List<EventResponseDto> getEventResponseDtos(List<Event> events) {
        return events.stream()
                .map(p -> EventResponseDto.builder()
                        .secureId(p.getId().toString())
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



        List<Event> events = new ArrayList<>();

        for (EventCategory category : eventCategories) {
            events.add(queryFactory.selectFrom(event)
                    .where(event.eventContest.id.eq(findContest.getId())
                            .and(event.eventCategory.eq(category)))
                    .fetchOne());
        }

        return getEventResponseDtos(events);
    }
}
