package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(nullable = false, name = "student_id")
    private String studentId;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String universityEmail;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberType memberType;

    @OneToMany(mappedBy = "contestMember", orphanRemoval = true)
    @Builder.Default
    private List<Contest> contests = new ArrayList<>();


    @OneToMany(mappedBy = "participateMember", orphanRemoval = true)
    @Builder.Default
    private List<ParticipateState> participateStates = new ArrayList<>();



}
