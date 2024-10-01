package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Event;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.entity.ParticipateState;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.EventType;
import sheetplus.checking.domain.entity.enums.MemberType;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
class ParticipateStateRepositoryTest {

    @Autowired
    ParticipateStateRepository participateStateRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EventRepository eventRepository;

    ParticipateState participateState;
    Member member;
    Event event;

    @BeforeEach
    void before(){
        member = Member.builder()
                .studentId("20191511")
                .major("Internet of Things")
                .universityEmail("king7292@sch.ac.kr")
                .memberType(MemberType.SUPER_ADMIN)
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
        participateState = ParticipateState.builder()
                .build();
    }

    @Test
    @DisplayName("Participate 객체 연관관계 테스트")
    void relationParticipateTest(){
        memberRepository.save(member);
        eventRepository.save(event);
        participateState.setMemberParticipate(member);
        participateState.setEventParticipate(event);

        Long id = participateStateRepository.save(participateState).getId();

        Member findMember = participateStateRepository.findById(id).get().getParticipateMember();
        Event findEvent = participateStateRepository.findById(id).get().getParticipateEvent();

        assertThat(findMember.getStudentId())
                .isEqualTo(member.getStudentId());
        assertThat(findEvent.getName())
                .isEqualTo(findEvent.getName());
    }

}