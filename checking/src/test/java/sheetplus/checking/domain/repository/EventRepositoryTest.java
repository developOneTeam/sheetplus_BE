package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Event;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.EventType;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EventRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ContestRepository contestRepository;

    Contest contest;
    Event event;

    @BeforeEach
    void before(){
        contest = Contest.builder()
                .name("학술제")
                .startDate("2024-11-01 09:00")
                .endDate("2024-11-01 16:00")
                .condition(ContestCondition.BEFORE)
                .build();
        event = Event.builder()
                .name("개회식")
                .startTime("2024-11-01 09:00")
                .endTime("2024-11-01 09:30")
                .speakerName("김승우 총장")
                .location("대학본관")
                .eventType(EventType.CHECKING)
                .eventCondition(ContestCondition.BEFORE)
                .build();
    }


    @Test
    @DisplayName("Event 객체 저장 기능 테스트")
    void saveEventTest(){
        Long id = eventRepository.save(event).getId();

        assertThat(eventRepository.findById(id).get().getName())
                .isEqualTo(event.getName());
        assertThat(eventRepository.findById(id).get().getEventCondition())
                .isEqualTo(event.getEventCondition());
        assertThat(eventRepository.findById(id).get().getEventType())
                .isEqualTo(event.getEventType());
    }

    @Test
    @DisplayName("Event 객체 연관관계 테스트")
    void relationEventTest(){
        contestRepository.save(contest);
        event.setContestEvent(contest);
        Long id = eventRepository.save(event).getId();

        Contest findContest = eventRepository.findById(id).get().getEventContest();

        assertThat(findContest.getName())
                .isEqualTo(contest.getName());
    }

}