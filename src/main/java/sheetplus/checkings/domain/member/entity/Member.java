package sheetplus.checkings.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.favorite.entity.Favorite;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberUpdateRequestDto;
import sheetplus.checkings.domain.draw.entity.Draw;
import sheetplus.checkings.domain.participatecontest.entity.ParticipateContest;
import sheetplus.checkings.domain.enums.MemberType;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@NamedEntityGraph(
        name = "member.withParticipateContest",
        attributeNodes ={
            @NamedAttributeNode(value = "memberParticipateContestStates", subgraph = "participateContest.withContest")
        },
        subgraphs = @NamedSubgraph(name = "participateContest.withContest", attributeNodes = {
                @NamedAttributeNode("contestParticipateContestState")
        })
)
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

    @Column(nullable = false, unique = true)
    private String universityEmail;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberType memberType;

    @OneToMany(mappedBy = "memberParticipateContestState", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<ParticipateContest> memberParticipateContestStates = new ArrayList<>();

    @OneToMany(mappedBy = "drawMember", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Draw> memberDraw = new ArrayList<>();

    @OneToMany(mappedBy = "favoriteMember", orphanRemoval = true)
    @Builder.Default
    private List<Favorite> favorites = new ArrayList<>();


    public void memberInfoUpdate(MemberUpdateRequestDto memberUpdateRequestDto){
        this.name = memberUpdateRequestDto.getName();
        this.major = memberUpdateRequestDto.getMajor();
        this.studentId = memberUpdateRequestDto.getStudentId();
    }

}
