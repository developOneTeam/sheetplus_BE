package sheetplus.checkings.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.dto.EventRequestDto;
import sheetplus.checkings.domain.entity.enums.ContestCons;
import sheetplus.checkings.domain.entity.enums.EventCategory;
import sheetplus.checkings.domain.entity.enums.EventType;


import java.time.LocalDateTime;

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
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

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
    private ContestCons eventCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory eventCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest eventContest;


    public void setContestEvent(Contest contest){
        this.eventContest = contest;
        eventContest.getEvents().add(this);
    }


    public void updateEvent(EventRequestDto eventRequestDto){
        this.name = eventRequestDto.getName();
        this.startTime = eventRequestDto.getStartTime();
        this.endTime = eventRequestDto.getEndTime();
        this.location = eventRequestDto.getLocation();
        this.building = eventRequestDto.getBuilding();
        this.speakerName = eventRequestDto.getSpeakerName();
        this.major = eventRequestDto.getMajor();
        this.eventType = eventRequestDto.getEventType();
        this.eventCondition = eventRequestDto.getCondition();
        this.eventCategory = eventRequestDto.getCategory();
    }

}
