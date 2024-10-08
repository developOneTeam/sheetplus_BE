package sheetplus.checking.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class ParticipateState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participate_state_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member participateMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event participateEvent;

    @OneToOne(mappedBy = "participateStatePrize")
    private Prize prizeParticipateState;

    @OneToOne(mappedBy = "participateStateDraw")
    private Draw drawParticipateState;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public void setMemberParticipate(Member member){
        this.participateMember = member;
        participateMember.getParticipateStates().add(this);
    }

    public void setEventParticipate(Event event){
        this.participateEvent = event;
        participateEvent.getParticipateStates().add(this);
    }

    public void setPrizeParticipateState(Prize prize){
        this.prizeParticipateState = prize;
        if(prizeParticipateState.getParticipateStatePrize() != this){
            prizeParticipateState.setParticipateStatePrize(this);
        }
    }

    public void setDrawParticipateState(Draw draw){
        this.drawParticipateState = draw;
        if(drawParticipateState.getParticipateStateDraw() != this){
            drawParticipateState.setParticipateStateDraw(this);
        }
    }
}
