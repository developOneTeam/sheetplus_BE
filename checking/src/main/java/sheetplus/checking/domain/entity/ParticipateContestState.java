package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sheetplus.checking.domain.entity.enums.EventType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class ParticipateContestState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participate_contest_state_id")
    private Long id;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberParticipateContestState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contestParticipateContestState;

    @ElementCollection
    @CollectionTable(
            name = "participate_event_type",
            joinColumns = @JoinColumn(name = "participate_contest_state_id")
    )
    @Builder.Default
    private Set<EventType> eventTypeSet = new HashSet<>();


    public void setMemberParticipateContestStates(Member member){
        this.memberParticipateContestState = member;
        member.getMemberParticipateContestStates().add(this);
    }

    public void setContestParticipateContestStates(Contest contest){
        this.contestParticipateContestState = contest;
        contest.getParticipateContestStates().add(this);
    }

}
