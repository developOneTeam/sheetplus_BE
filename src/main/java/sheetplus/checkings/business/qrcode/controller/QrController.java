package sheetplus.checkings.business.qrcode.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeCreateRequestDto;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeCreateResponseDto;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeRequestDto;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeResponseDto;
import sheetplus.checkings.business.qrcode.service.QrcodeService;
import sheetplus.checkings.util.JwtUtil;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private")
public class QrController implements QrControllerSpec{

    private final QrcodeService qrcodeService;
    private final JwtUtil jwtUtil;

    @PostMapping("/student/qrcode/v1")
    public ResponseEntity<QrcodeResponseDto> qrcodeCheck(
            @RequestHeader(value = "Authorization", required = false)
            String token,
            @RequestBody @Validated QrcodeRequestDto qrcodeRequestDto){

        QrcodeResponseDto qrcodeResponseDto
                = qrcodeService.createParticipation(
                jwtUtil.getMemberId(token.replace("Bearer","").trim()), qrcodeRequestDto);

        return ResponseEntity.ok(qrcodeResponseDto);
    }

    @PostMapping("/admin/events/qrcode/v1")
    public ResponseEntity<QrcodeCreateResponseDto> createQrcode(
            @RequestHeader(value = "Authorization", required = false)
            String token,
            @RequestBody @Validated QrcodeCreateRequestDto qrcodeCreateRequestDto){

        QrcodeCreateResponseDto qrcodeCreateResponseDto
                = qrcodeService.createQrcode(
                        token.replace("Bearer","").trim(), qrcodeCreateRequestDto.getEventId());

        return ResponseEntity.ok()
                .header("Cache-Control", "no-store")
                .body(qrcodeCreateResponseDto);
    }

}
