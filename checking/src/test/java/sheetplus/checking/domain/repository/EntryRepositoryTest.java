package sheetplus.checking.domain.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Entry;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.EntryType;

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
                .location("미디어랩스 4층")
                .teamName("가지각색")
                .entryType(EntryType.PRELIMINARY)
                .build();
        contest = Contest.builder()
                .name("학술제")
                .startDate("2024-11-01 09:00")
                .endDate("2024-11-01 16:00")
                .condition(ContestCondition.BEFORE)
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