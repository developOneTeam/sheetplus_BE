package sheetplus.checkings.business.qrcode.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.qrcode.dto.QrcodeCreateResponseDto;
import sheetplus.checkings.business.qrcode.dto.QrcodeRequestDto;
import sheetplus.checkings.business.qrcode.dto.QrcodeResponseDto;
import sheetplus.checkings.business.qrcode.service.QrcodeService;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private")
public class QrController {

    private final QrcodeService qrcodeService;

    @PostMapping("/student/qrcode/v1")
    public ResponseEntity<QrcodeResponseDto> qrcodeCheck(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody QrcodeRequestDto qrcodeRequestDto){

        QrcodeResponseDto qrcodeResponseDto
                = qrcodeService.createParticipation(
                        token.replace("Bearer","").trim(), qrcodeRequestDto);

        return ResponseEntity.ok(qrcodeResponseDto);
    }

    @GetMapping("/admin/events/{eventId}/qrcode/v1")
    public ResponseEntity<QrcodeCreateResponseDto> createQrcode(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "eventId") Long id){

        QrcodeCreateResponseDto qrcodeCreateResponseDto
                = qrcodeService.createQrcode(
                        token.replace("Bearer","").trim(), id);

        return ResponseEntity.ok()
                .header("Cache-Control", "no-store")
                .body(qrcodeCreateResponseDto);
    }

}
