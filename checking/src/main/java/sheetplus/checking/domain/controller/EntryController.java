package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.EntryRequestDto;
import sheetplus.checking.domain.dto.EntryResponseDto;
import sheetplus.checking.domain.service.EntryCRUDService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EntryController {

    private final EntryCRUDService entryCRUDService;

    @PostMapping("private/admin/entry/create")
    public ResponseEntity<EntryResponseDto> createEntry(
            @RequestBody EntryRequestDto entryRequestDto) {

        return ResponseEntity.ok()
                .body(entryCRUDService.createEntry(entryRequestDto));
    }


    @PatchMapping("private/admin/entry/{entry}/update")
    public ResponseEntity<EntryResponseDto> updateEntry(
            @PathVariable(name = "entry") Long entryId,
            @RequestBody EntryRequestDto entryRequestDto
    ){
        return ResponseEntity.ok()
                .body(entryCRUDService.updateEntry(entryId, entryRequestDto));
    }

    @DeleteMapping("private/admin/entry{entry}/delete")
    public ResponseEntity<String> deleteEntry(
            @PathVariable(name = "entry") Long entryId
    ){
        entryCRUDService.deleteEntry(entryId);
        return ResponseEntity.ok()
                .body("삭제 완료");
    }

}
