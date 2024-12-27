package sheetplus.checkings.domain.entry.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.entry.dto.request.EntryRequestDto;
import sheetplus.checkings.domain.entry.dto.response.EntryResponseDto;
import sheetplus.checkings.domain.entry.service.EntryCRUDService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/admin")
@Slf4j
public class EntryController {

    private final EntryCRUDService entryCRUDService;

    @PostMapping("/contests/{contest}/entry")
    public ResponseEntity<EntryResponseDto> createEntry(
            @PathVariable(name = "contest") Long contestId,
            @RequestBody EntryRequestDto entryRequestDto) {

        return ResponseEntity.created(URI.create(""))
                .body(entryCRUDService.createEntry(contestId, entryRequestDto));
    }


    @PatchMapping("/entries/{entry}")
    public ResponseEntity<EntryResponseDto> updateEntry(
            @PathVariable(name = "entry") Long entryId,
            @RequestBody EntryRequestDto entryRequestDto
    ){
        return ResponseEntity.ok(entryCRUDService.updateEntry(entryId, entryRequestDto));
    }

    @DeleteMapping("/entries/{entry}")
    public ResponseEntity<String> deleteEntry(
            @PathVariable(name = "entry") Long entryId
    ){
        entryCRUDService.deleteEntry(entryId);
        return ResponseEntity.noContent().build();
    }

}
