package sheetplus.checkings.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.entity.enums.ReceiveCons;


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
    private ReceiveCons receiveCons;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest drawContest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member drawMember;

    public void setContestDraw(Contest contestDraw) {
        this.drawContest = contestDraw;
        drawContest.getDraws().add(this);
    }

    public void setMemberDraw(Member member){
        this.drawMember = member;
        if(drawMember.getMemberDraw() != this){
            member.setDrawMember(this);
        }

    }

    public void setReceiveCons(ReceiveCons receiveCons) {
        this.receiveCons = receiveCons;
    }

}
