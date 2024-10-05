package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.entity.enums.MemberType;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void before(){
        member = Member.builder()
                .studentId("20191511")
                .major("Internet of Things")
                .universityEmail("king7292@sch.ac.kr")
                .memberType(MemberType.SUPER_ADMIN)
                .build();
    }

    @Test
    @DisplayName("멤버 객체 저장기능 테스트")
    void saveMemberTest(){
        Long id = memberRepository.save(member).getId();

        assertThat(memberRepository.findById(id).get().getStudentId())
                .isEqualTo(member.getStudentId());
        assertThat(memberRepository.findById(id).get().getMemberType())
                .isEqualTo(member.getMemberType());
    }


}