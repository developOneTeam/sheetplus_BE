package sheetplus.checking.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Draw;
import sheetplus.checking.domain.entity.ParticipateState;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.ReceiveCondition;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class DrawRepositoryTest {

    @Autowired
    DrawRepository drawRepository;

    @Autowired
    ContestRepository contestRepository;

    @Autowired
    ParticipateStateRepository participateStateRepository;

    Draw draw;
    Contest contest;
    ParticipateState participateState;

    @BeforeEach
    void before(){
        draw = Draw.builder()
                .receiveCondition(ReceiveCondition.NOT_RECEIVED)
                .drawType("1등")
                .build();
        contest = Contest.builder()
                .name("학술제")
                .startDate("2024-11-01 09:00")
                .endDate("2024-11-01 16:00")
                .condition(ContestCondition.BEFORE)
                .build();
        participateState = ParticipateState.builder()
                .build();
    }

    @Test
    @DisplayName("Draw 객체 저장 기능 테스트")
    void saveDrawTest(){
        Long id = drawRepository.save(draw).getId();

        assertThat(drawRepository.findById(id).get().getDrawType())
                .isEqualTo(draw.getDrawType());
    }

    @Test
    @DisplayName("Draw 객체 연관관계 테스트")
    void relationDrawTest(){
        contestRepository.save(contest);
        participateStateRepository.save(participateState);
        draw.setContestDraw(contest);
        draw.setParticipateStateDraw(participateState);
        Long id = drawRepository.save(draw).getId();

        assertThat(drawRepository.findById(id).get().getContestDraw().getName())
                .isEqualTo(contest.getName());
        assertThat(drawRepository.findById(id).get().getParticipateStateDraw().getCreatedAt())
                .isEqualTo(participateState.getCreatedAt());
    }

}