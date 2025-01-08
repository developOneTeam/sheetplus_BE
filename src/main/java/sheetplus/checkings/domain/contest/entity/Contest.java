package sheetplus.checkings.domain.contest.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestRequestDto;
import sheetplus.checkings.domain.draw.entity.Draw;
import sheetplus.checkings.domain.entry.entity.Entry;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.participatecontest.entity.ParticipateContest;
import sheetplus.checkings.domain.enums.ContestCons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestCons cons;

    @OneToOne(mappedBy = "contestParticipateContestState")
    private ParticipateContest participateContestStateContest;

    @OneToMany(mappedBy = "entryContest", orphanRemoval = true)
    @Builder.Default
    private List<Entry> entries = new ArrayList<>();

    @OneToMany(mappedBy = "eventContest", orphanRemoval = true)
    @Builder.Default
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "drawContest", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Draw> draws = new ArrayList<>();


    public void updateContest(ContestRequestDto contestRequestDto){
        this.name = contestRequestDto.getName();
        this.startDate = contestRequestDto.getStartDateTime();
        this.endDate = contestRequestDto.getEndDateTime();
        this.cons = contestRequestDto.getCondition();
    }

}
