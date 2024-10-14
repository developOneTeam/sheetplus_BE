package sheetplus.checking.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Draw;
import sheetplus.checking.domain.entity.enums.ContestCons;
import sheetplus.checking.domain.entity.enums.ReceiveCons;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class DrawRepositoryTest {

    @Autowired
    DrawRepository drawRepository;

    @Autowired
    ContestRepository contestRepository;

    Draw draw;
    Contest contest;

    @BeforeEach
    void before(){
        draw = Draw.builder()
                .receiveCons(ReceiveCons.PRIZE_NOT_RECEIVED)
                .drawType("1등")
                .build();
        contest = Contest.builder()
                .name("학술제")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .cons(ContestCons.EVENT_BEFORE)
                .build();
    }

    @Test
    @DisplayName("Draw 객체 저장 기능 테스트")
    void saveDrawTest(){
        Long id = drawRepository.save(draw).getId();

        assertThat(drawRepository.findById(id).get().getDrawType())
                .isEqualTo(draw.getDrawType());
    }


}