package sheetplus.checking.domain.repository;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Prize;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.ReceiveCondition;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PrizeRepositoryTest {

    @Autowired
    PrizeRepository prizeRepository;

    @Autowired
    ContestRepository contestRepository;

    @Autowired
    ParticipateStateRepository participateStateRepository;

    Prize prize;
    Contest contest;

    @BeforeEach
    void before(){
        prize = Prize.builder()
                .receiveCondition(ReceiveCondition.NOT_RECEIVED)
                .build();
        contest = Contest.builder()
                .name("학술제")
                .startDate("2024-11-01 09:00")
                .endDate("2024-11-01 16:00")
                .condition(ContestCondition.BEFORE)
                .build();
    }

    @Test
    @DisplayName("Prize 객체 저장 기능 테스트")
    void savePrizeTest(){
        Long id = prizeRepository.save(prize).getId();

        assertThat(prizeRepository.findById(id).get().getReceiveCondition())
                .isEqualTo(prize.getReceiveCondition());
    }

}