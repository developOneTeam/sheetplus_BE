package sheetplus.checkings.business.qrcode.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
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
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeCreateResponseDto;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeRequestDto;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeResponseDto;
import sheetplus.checkings.business.qrcode.service.QrcodeService;
import sheetplus.checkings.exception.error.ErrorResponse;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private")
@Tag(name = "QRcode", description = "QRcode Service API")
public class QrController {

    private final QrcodeService qrcodeService;

    @PostMapping("/student/qrcode/v1")
    @Operation(summary = "QRcode register", description = "QRcode 인증을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QRcode 인증을 성공했습니다. 참여자로 등록합니다.",
                    content = @Content(schema = @Schema(implementation = QrcodeResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다, 이미 만료된 QR코드 요청입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Member or Event] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409",
                    description = "QRcode를 인증 이벤트가 아닙니다, 현재 진행중인 이벤트가 아닙니다, 이미 참여한 행사입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<QrcodeResponseDto> qrcodeCheck(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody @Validated QrcodeRequestDto qrcodeRequestDto){

        QrcodeResponseDto qrcodeResponseDto
                = qrcodeService.createParticipation(
                        token.replace("Bearer","").trim(), qrcodeRequestDto);

        return ResponseEntity.ok(qrcodeResponseDto);
    }

    @GetMapping("/admin/events/{eventId}/qrcode/v1")
    @Operation(summary = "QRcode CREATE", description = "QRcode를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QRcode를 생성합니다",
                    content = @Content(schema = @Schema(implementation = QrcodeCreateResponseDto.class),
                            mediaType = "application/json"),
                    headers =@Header(name = "Cache-Control", description = "보안키가 포함되어 있으므로 캐싱하면 안 됩니다.")
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다, 이미 만료된 QR코드 요청입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Member or Event] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "QRcode를 인증 이벤트가 아닙니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<QrcodeCreateResponseDto> createQrcode(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token,

            @Parameter(description = "Event PK", example = "1")
            @PathVariable(name = "eventId") Long id){

        QrcodeCreateResponseDto qrcodeCreateResponseDto
                = qrcodeService.createQrcode(
                        token.replace("Bearer","").trim(), id);

        return ResponseEntity.ok()
                .header("Cache-Control", "no-store")
                .body(qrcodeCreateResponseDto);
    }

}
