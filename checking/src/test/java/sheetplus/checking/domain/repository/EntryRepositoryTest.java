package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Entry;
import sheetplus.checking.domain.entity.enums.ContestCons;
import sheetplus.checking.domain.entity.enums.EntryType;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EntryRepositoryTest {

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    ContestRepository contestRepository;

    Entry entry;
    Contest contest;

    @BeforeEach
    void before(){
        entry = Entry.builder()
                .name("학내순환버스 도착/출발정보 안내 서비스")
                .building("미디어랩스")
                .location("4층")
                .teamNumber("IoT-416")
                .professorName("전창완")
                .leaderName("황제연")
                .entryType(EntryType.PRELIMINARY)
                .build();
        contest = Contest.builder()
                .name("학술제")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .cons(ContestCons.EVENT_BEFORE)
                .build();
    }

    @Test
    @DisplayName("Entry 객체 저장 기능 테스트")
    void saveEntryTest(){
        Long id = entryRepository.save(entry).getId();

        assertThat(entryRepository.findById(id).get().getName())
                .isEqualTo(entry.getName());
    }

    @Test
    @DisplayName("Entry 객체 연관관계 테스트")
    void findEntryTest(){
        contestRepository.save(contest);
        entry.setContestEntry(contest);
        Long id = entryRepository.save(entry).getId();

        assertThat(entryRepository.findById(id).get().getEntryContest().getName())
                .isEqualTo(contest.getName());
    }


}