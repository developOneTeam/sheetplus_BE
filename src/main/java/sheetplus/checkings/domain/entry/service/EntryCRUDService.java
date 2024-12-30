package sheetplus.checkings.domain.entry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.entry.dto.request.EntryRequestDto;
import sheetplus.checkings.domain.entry.dto.response.EntryResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.entry.entity.Entry;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.entry.repository.EntryRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;
import static sheetplus.checkings.exception.error.ApiError.ENTRY_NOT_FOUND;

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
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
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
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ENTRY_NOT_FOUND));;
        entry.updateEntry(entryRequestDto);


        return EntryResponseDto.builder()
                .id(id)
                .name(entry.getName())
                .location(entry.getLocation())
                .building(entry.getBuilding())
                .teamNumber(entry.getTeamNumber())
                .major(entry.getMajor())
                .professorName(entry.getProfessorName())
                .leaderName(entry.getLeaderName())
                .entryType(entry.getEntryType().getMessage())
                .build();
    }

    @Transactional
    public void deleteEntry(Long id){
        if(entryRepository.existsById(id)){
            entryRepository.deleteById(id);
        }else{
            throw new ApiException(ENTRY_NOT_FOUND);
        }
    }


}
