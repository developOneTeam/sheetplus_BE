package sheetplus.checkings.domain.draw.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.enums.ReceiveCons;
import sheetplus.checkings.domain.member.entity.Member;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Draw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "draw_id")
    private Long id;

    @Column(nullable = false)
    private String drawType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private ReceiveCons receiveCons;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest drawContest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member drawMember;

    public void setContestDraw(Contest contestDraw) {
        this.drawContest = contestDraw;
        drawContest.getDraws().add(this);
    }

    public void setMemberDraw(Member member){
        this.drawMember = member;
        drawMember.getMemberDraw().add(this);
    }


}
