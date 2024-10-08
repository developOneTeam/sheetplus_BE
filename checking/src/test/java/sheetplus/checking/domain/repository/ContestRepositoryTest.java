package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.MemberType;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ContestRepositoryTest {

    @Autowired
    ContestRepository contestRepository;

    Contest contest;
    Member member;
    @Autowired
    private MemberRepository memberRepository;

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
                .startDate("2024-11-01 09:00")
                .endDate("2024-11-01 16:00")
                .condition(ContestCondition.BEFORE)
                .build();
    }

    @Test
    @DisplayName("대회 객체 저장기능 테스트")
    void saveContestTest(){
        Long contestId = contestRepository.save(contest).getId();

        assertThat(contestRepository.findById(contestId).get().getName())
                .isEqualTo(contest.getName());
        assertThat(contestRepository.findById(contestId).get().getCondition())
                .isEqualTo(contest.getCondition());
    }

    @Test
    @DisplayName("멤버 객체와의 연관관계 테스트")
    void relationContestTest(){
        memberRepository.save(member);
        contest.setMemberContest(member);
        Long contestId = contestRepository.save(contest).getId();

        Contest findContest = contestRepository.findById(contestId).get();

        assertThat(member.getStudentId())
                .isEqualTo(findContest.getContestMember().getStudentId());
    }



}