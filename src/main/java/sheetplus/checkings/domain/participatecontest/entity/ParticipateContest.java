package sheetplus.checkings.domain.participatecontest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.enums.EventCategory;
import sheetplus.checkings.domain.enums.MeritType;
import sheetplus.checkings.domain.enums.ReceiveCons;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReceiveCons receiveCons;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeritType meritType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member memberParticipateContestState;

    @OneToOne
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

    @OneToMany(mappedBy = "eventParticipateContest")
    @Builder.Default
    private List<Event> eventParticipateContest = new ArrayList<>();


    public void setMemberParticipateContestStates(Member member){
        this.memberParticipateContestState = member;
        member.getMemberParticipateContestStates().add(this);
    }

    public void setContestParticipateContestStates(Contest contest){
        this.contestParticipateContestState = contest;
    }

    public void addCounts(){
        this.eventsCount++;
    }

}
