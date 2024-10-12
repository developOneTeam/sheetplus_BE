package sheetplus.checking.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.EntryRequestDto;
import sheetplus.checking.domain.dto.EntryResponseDto;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Entry;
import sheetplus.checking.domain.repository.ContestRepository;
import sheetplus.checking.domain.repository.EntryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntryCRUDService {

    private final EntryRepository entryRepository;
    private final ContestRepository contestRepository;


    @Transactional
    public EntryResponseDto createEntry(Long contestId, EntryRequestDto entryRequestDto){
        Entry entry = Entry.builder()
                .name(entryRequestDto.getName())
                .location(entryRequestDto.getLocation())
                .building(entryRequestDto.getBuilding())
                .teamNumber(entryRequestDto.getTeamNumber())
                .professorName(entryRequestDto.getProfessorName())
                .major(entryRequestDto.getMajor())
                .leaderName(entryRequestDto.getLeaderName())
                .entryType(entryRequestDto.getEntryType())
                .build();
        Contest contest = contestRepository.findById(contestId).orElse(null);
        entry.setContestEntry(contest);
        Long id = entryRepository.save(entry).getId();

        return EntryResponseDto.builder()
                .id(id)
                .name(entry.getName())
                .location(entry.getLocation())
                .building(entry.getBuilding())
                .teamNumber(entry.getTeamNumber())
                .professorName(entry.getProfessorName())
                .leaderName(entry.getLeaderName())
                .major(entry.getMajor())
                .entryType(entry.getEntryType().getMessage())
                .build();
    }

    @Transactional
    public EntryResponseDto updateEntry(Long id, EntryRequestDto entryRequestDto){
        Entry entry = entryRepository.findById(id).orElse(null);
        entry.updateEntry(entryRequestDto);


        return EntryResponseDto.builder()
                .id(id)
                .name(entry.getName())
                .location(entry.getLocation())
                .building(entry.getBuilding())
                .teamNumber(entry.getTeamNumber())
                .professorName(entry.getProfessorName())
                .leaderName(entry.getLeaderName())
                .entryType(entry.getEntryType().getMessage())
                .build();
    }

    @Transactional
    public void deleteEntry(Long id){
        entryRepository.deleteById(id);
    }


}
