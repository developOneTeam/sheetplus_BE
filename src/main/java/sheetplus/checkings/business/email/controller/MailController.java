package sheetplus.checkings.business.email.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.business.email.dto.EmailDto.EmailRequestDto;
import sheetplus.checkings.business.email.service.EmailService;
import sheetplus.checkings.exception.error.ErrorResponse;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("public")
@Tag(name = "Email", description = "Email Service API")
public class MailController {

    private final EmailService emailService;

    @PostMapping("/auth/mails/v1")
    @Operation(summary = "Sending Email", description = "인증 이메일을 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "인증 이메일 발송",
                    content = @Content(schema = @Schema(implementation = EmailRequestDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "학교 이메일이 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    public ResponseEntity<Void> sendMail(
            @RequestBody @Validated EmailRequestDto emailRequestDto){
        emailService.verifyEmailDomain(emailRequestDto.getReceiver());
        String code = emailService.createCode();

        emailService.sendEmail(emailRequestDto.getReceiver(),
                emailService.registerCheck(emailRequestDto.getReceiver()), code);

        emailService.createTemporaryMember(emailRequestDto.getReceiver(),
                code);
        return ResponseEntity.accepted().build();
    }

}
