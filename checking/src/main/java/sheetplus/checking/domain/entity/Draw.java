package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checking.domain.entity.enums.ReceiveCondition;


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
    private ReceiveCondition receiveCondition;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "participate_state_id")
    private ParticipateState participateStateDraw;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contestDraw;

    public void setParticipateStateDraw(ParticipateState participateState) {
        this.participateStateDraw = participateState;
        if(participateStateDraw.getDrawParticipateState() != this){
            participateStateDraw.setDrawParticipateState(this);
        }
    }

    public void setContestDraw(Contest contestDraw) {
        this.contestDraw = contestDraw;
        contestDraw.getDraws().add(this);
    }

}
