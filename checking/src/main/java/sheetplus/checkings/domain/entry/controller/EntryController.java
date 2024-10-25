package sheetplus.checkings.domain.entry.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.entry.dto.request.EntryRequestDto;
import sheetplus.checkings.domain.entry.dto.response.EntryResponseDto;
import sheetplus.checkings.domain.entry.service.EntryCRUDService;
import sheetplus.checkings.util.response.Api;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EntryController {

    private final EntryCRUDService entryCRUDService;

    @PostMapping("private/admin/contest/{contest}/entry/create")
    public Api<EntryResponseDto> createEntry(
            @PathVariable(name = "contest") Long contestId,
            @RequestBody EntryRequestDto entryRequestDto) {

        return Api.CREATED(entryCRUDService.createEntry(contestId, entryRequestDto));
    }


    @PatchMapping("private/admin/entry/{entry}/update")
    public Api<EntryResponseDto> updateEntry(
            @PathVariable(name = "entry") Long entryId,
            @RequestBody EntryRequestDto entryRequestDto
    ){
        return Api.UPDATED(entryCRUDService.updateEntry(entryId, entryRequestDto));
    }

    @DeleteMapping("private/admin/entry/{entry}/delete")
    public Api<String> deleteEntry(
            @PathVariable(name = "entry") Long entryId
    ){
        entryCRUDService.deleteEntry(entryId);
        return Api.DELETE("삭제 완료");
    }

}
