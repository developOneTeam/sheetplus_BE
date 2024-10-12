package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checking.domain.dto.MemberUpdateRequestDto;
import sheetplus.checking.domain.entity.enums.MemberType;


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

    @OneToOne(mappedBy = "memberParticipateContestState")
    private ParticipateContestState memberParticipateContestStates;

    @OneToOne(mappedBy = "drawMember")
    private Draw memberDraw;


    public void memberInfoUpdate(MemberUpdateRequestDto memberUpdateRequestDto){
        this.name = memberUpdateRequestDto.getName();
        this.major = memberUpdateRequestDto.getMajor();
        this.studentId = memberUpdateRequestDto.getStudentId();
    }

    public void setMemberParticipateContestStates(
            ParticipateContestState memberParticipateContestStates) {
        this.memberParticipateContestStates = memberParticipateContestStates;
        if(memberParticipateContestStates.getMemberParticipateContestState() != this){
            memberParticipateContestStates.setMemberParticipateContestStates(this);
        }
    }

    public void setDrawMember(Draw draw){
        this.memberDraw = draw;
        if(memberDraw.getDrawMember() != this){
            memberDraw.setMemberDraw(this);
        }
    }

}
