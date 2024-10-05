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
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prize_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReceiveCondition receiveCondition;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "participate_state_id")
    private ParticipateState participateStatePrize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contestPrize;

    public void setParticipateStatePrize(ParticipateState participateState) {
        this.participateStatePrize = participateState;
        if(participateStatePrize.getPrizeParticipateState() != this){
            participateStatePrize.setPrizeParticipateState(this);
        }
    }

    public void setContestPrize(Contest contestPrize) {
        this.contestPrize = contestPrize;
        contestPrize.getPrizes().add(this);
    }


}
