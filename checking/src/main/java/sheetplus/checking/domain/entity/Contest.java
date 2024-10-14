package sheetplus.checking.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import sheetplus.checking.domain.dto.ContestRequestDto;
import sheetplus.checking.domain.entity.enums.ContestCons;

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
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestCons cons;

    @OneToMany(mappedBy = "contestParticipateContestState")
    @Builder.Default
    private List<ParticipateContest> participateContests = new ArrayList<>();

    @OneToMany(mappedBy = "entryContest", orphanRemoval = true)
    @Builder.Default
    private List<Entry> entries = new ArrayList<>();

    @OneToMany(mappedBy = "eventContest", orphanRemoval = true)
    @Builder.Default
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "drawContest", orphanRemoval = true)
    @Builder.Default
    private List<Draw> draws = new ArrayList<>();


    public void updateContest(ContestRequestDto contestRequestDto){
        this.name = contestRequestDto.getName();
        this.startDate = contestRequestDto.getStartDateTime();
        this.endDate = contestRequestDto.getEndDateTime();
        this.cons = contestRequestDto.getCondition();
    }

}
