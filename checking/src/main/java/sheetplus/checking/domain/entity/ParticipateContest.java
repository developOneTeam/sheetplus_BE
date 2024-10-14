package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sheetplus.checking.domain.entity.enums.EventCategory;
import sheetplus.checking.domain.entity.enums.MeritType;
import sheetplus.checking.domain.entity.enums.ReceiveCons;

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
public class ParticipateContest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participate_contest_state_id")
    private Long id;

    @Column(nullable = false)
    private Integer eventsCount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReceiveCons receiveCons;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeritType meritType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberParticipateContestState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contestParticipateContestState;

    @ElementCollection
    @CollectionTable(
            name = "participate_event_type",
            joinColumns = @JoinColumn(name = "participate_contest_id")
    )
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<EventCategory> eventTypeSet = new HashSet<>();


    public void setMemberParticipateContestStates(Member member){
        this.memberParticipateContestState = member;
        if(this.memberParticipateContestState.getMemberParticipateContestStates() != this){
            member.setMemberParticipateContestStates(this);
        }
    }

    public void setContestParticipateContestStates(Contest contest){
        this.contestParticipateContestState = contest;
        contest.getParticipateContests().add(this);
    }

    public void addCounts(){
        this.eventsCount++;
    }

}
