package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.entity.enums.ContestCons;
import sheetplus.checking.domain.entity.enums.MemberType;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ContestRepositoryTest {

    @Autowired
    ContestRepository contestRepository;

    Contest contest;
    Member member;

    @BeforeEach
    void before(){
        member = Member.builder()
                .studentId("20191511")
                .major("Internet of Things")
                .universityEmail("king7292@sch.ac.kr")
                .memberType(MemberType.SUPER_ADMIN)
                .build();
        contest = Contest.builder()
                .name("학술제")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .cons(ContestCons.EVENT_BEFORE)
                .build();
    }

    @Test
    @DisplayName("대회 객체 저장기능 테스트")
    void saveContestTest(){
        Long contestId = contestRepository.save(contest).getId();

        assertThat(contestRepository.findById(contestId).get().getName())
                .isEqualTo(contest.getName());
        assertThat(contestRepository.findById(contestId).get().getCons())
                .isEqualTo(contest.getCons());
    }



}