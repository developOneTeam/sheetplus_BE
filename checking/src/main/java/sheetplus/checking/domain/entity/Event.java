package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checking.domain.entity.enums.ContestCategory;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.EventType;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String building;

    @Column(nullable = false)
    private String speakerName;

    @Column(nullable = false)
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestCondition eventCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestCategory contestCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest eventContest;

    @OneToMany(mappedBy = "participateEvent", orphanRemoval = true)
    @Builder.Default
    private List<ParticipateState> participateStates = new ArrayList<>();

    public void setContestEvent(Contest contest){
        this.eventContest = contest;
        eventContest.getEvents().add(this);
    }

}
