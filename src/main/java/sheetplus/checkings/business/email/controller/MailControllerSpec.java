package sheetplus.checkings.business.email.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sheetplus.checkings.business.email.dto.EmailDto.EmailRequestDto;
import sheetplus.checkings.exception.error.ErrorResponse;

@Tag(name = "Email", description = "Email Service API")
public interface MailControllerSpec {

    @Operation(summary = "Sending Email", description = "인증 이메일을 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "인증 이메일 발송",
                    content = {@Content(mediaType = "None")}),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "학교 이메일이 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    ResponseEntity<Void> sendMail(EmailRequestDto emailRequestDto);

}
