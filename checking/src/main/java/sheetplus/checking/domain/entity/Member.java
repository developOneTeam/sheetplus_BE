package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checking.domain.dto.MemberUpdateRequestDto;
import sheetplus.checking.domain.entity.enums.MemberType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "student_id")
    private String studentId;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String universityEmail;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberType memberType;

    @OneToMany(mappedBy = "memberParticipateContestState")
    @Builder.Default
    private List<ParticipateContestState> memberParticipateContestStates = new ArrayList<>();


    @OneToMany(mappedBy = "participateMember")
    @Builder.Default
    private List<ParticipateState> participateStates = new ArrayList<>();


    public void memberInfoUpdate(MemberUpdateRequestDto memberUpdateRequestDto){
        this.name = memberUpdateRequestDto.getName();
        this.major = memberUpdateRequestDto.getMajor();
        this.studentId = memberUpdateRequestDto.getStudentId();
    }

}
