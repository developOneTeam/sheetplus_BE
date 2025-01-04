package sheetplus.checkings.domain.entry.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryRequestDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryResponseDto;
import sheetplus.checkings.domain.entry.service.EntryCRUDService;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/admin")
@Slf4j
@Tag(name = "Entry", description = "Entry CUD API")
public class EntryController {

    private final EntryCRUDService entryCRUDService;

    @PostMapping("/contests/{contest}/entry/v1")
    @Operation(summary = "Entry CREATE", description = "Entry를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entry를 생성했습니다",
                    content = @Content(schema = @Schema(implementation = EntryResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청, 요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Contest] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<EntryResponseDto> createEntry(
            @Parameter(description = "Contest PK", example = "1")
            @PathVariable(name = "contest") Long contestId,
            @RequestBody @Validated EntryRequestDto entryRequestDto) {

        return ResponseEntity.created(URI.create(""))
                .body(entryCRUDService.createEntry(contestId, entryRequestDto));
    }


    @PutMapping("/entries/{entry}/v1")
    @Operation(summary = "Entry UPDATE", description = "Entry데이터를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry데이터를 업데이트합니다",
                    content = @Content(schema = @Schema(implementation = EntryResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청, 요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Entry] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<EntryResponseDto> updateEntry(
            @Parameter(description = "Entry PK", example = "1")
            @PathVariable(name = "entry") Long entryId,
            @RequestBody @Validated EntryRequestDto entryRequestDto
    ){
        return ResponseEntity.ok(entryCRUDService.updateEntry(entryId, entryRequestDto));
    }

    @DeleteMapping("/entries/{entry}/v1")
    @Operation(summary = "Entry DELETE", description = "Entry를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entry를 삭제합니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Entry] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<String> deleteEntry(
            @Parameter(description = "Entry PK", example = "1")
            @PathVariable(name = "entry") Long entryId
    ){
        entryCRUDService.deleteEntry(entryId);
        return ResponseEntity.noContent().build();
    }

}
