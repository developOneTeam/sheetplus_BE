package sheetplus.checking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checking.domain.entity.enums.EntryType;

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
    private String teamName;

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest entryContest;

    public void setContestEntry(Contest contest){
        this.entryContest = contest;
        entryContest.getEntries().add(this);
    }

}
