package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Event;
import sheetplus.checking.domain.entity.enums.ContestCons;
import sheetplus.checking.domain.entity.enums.EventCategory;
import sheetplus.checking.domain.entity.enums.EventType;

import java.time.LocalDateTime;

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
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .cons(ContestCons.EVENT_BEFORE)
                .build();
        event = Event.builder()
                .name("개회식")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .speakerName("김승우 총장")
                .building("인문대")
                .location("101호")
                .major("SW융합대학")
                .eventType(EventType.CHECKING)
                .eventCondition(ContestCons.EVENT_BEFORE)
                .eventCategory(EventCategory.EVENT_ONE)
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