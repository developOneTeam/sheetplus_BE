package sheetplus.checking.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import sheetplus.checking.domain.entity.enums.ContestCondition;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestCondition condition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member contestMember;

    @OneToMany(mappedBy = "entryContest", orphanRemoval = true)
    @Builder.Default
    private List<Entry> entries = new ArrayList<>();

    @OneToMany(mappedBy = "eventContest", orphanRemoval = true)
    @Builder.Default
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "contestPrize", orphanRemoval = true)
    @Builder.Default
    private List<Prize> prizes = new ArrayList<>();

    @OneToMany(mappedBy = "contestDraw", orphanRemoval = true)
    @Builder.Default
    private List<Draw> draws = new ArrayList<>();

    public void setMemberContest(Member member){
        this.contestMember = member;
        contestMember.getContests().add(this);
    }

}
