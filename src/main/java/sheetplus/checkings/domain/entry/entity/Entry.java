package sheetplus.checkings.domain.entry.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryRequestDto;
import sheetplus.checkings.domain.enums.EntryType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String building;

    @Column(nullable = false)
    private String teamNumber;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String professorName;

    @Column(nullable = false)
    private String leaderName;

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest entryContest;

    public void setContestEntry(Contest contest){
        this.entryContest = contest;
        entryContest.getEntries().add(this);
    }

    public void updateEntry(EntryRequestDto entryRequestDto){
        this.name = entryRequestDto.getName();
        this.location = entryRequestDto.getLocation();
        this.building = entryRequestDto.getBuilding();
        this.teamNumber = entryRequestDto.getTeamNumber();
        this.professorName = entryRequestDto.getProfessorName();
        this.leaderName = entryRequestDto.getLeaderName();
        this.entryType = entryRequestDto.getEntryType();
    }

}
