package sheetplus.checkings.domain.entry.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryRequestDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryResponseDto;
import sheetplus.checkings.domain.entry.service.EntryCRUDService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/admin")
@Slf4j
public class EntryController implements EntryControllerSpec{

    private final EntryCRUDService entryCRUDService;

    @PostMapping("/contests/{contest}/entry/v1")
    public ResponseEntity<EntryResponseDto> createEntry(
            @PathVariable(name = "contest") Long contestId,
            @RequestBody @Validated EntryRequestDto entryRequestDto) {

        return ResponseEntity.created(URI.create(""))
                .body(entryCRUDService.createEntry(contestId, entryRequestDto));
    }


    @PutMapping("/entries/{entry}/v1")
    public ResponseEntity<EntryResponseDto> updateEntry(
            @PathVariable(name = "entry") Long entryId,
            @RequestBody @Validated EntryRequestDto entryRequestDto
    ){
        return ResponseEntity.ok(entryCRUDService.updateEntry(entryId, entryRequestDto));
    }

    @DeleteMapping("/entries/{entry}/v1")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable(name = "entry") Long entryId
    ){
        entryCRUDService.deleteEntry(entryId);
        return ResponseEntity.noContent().build();
    }

}
