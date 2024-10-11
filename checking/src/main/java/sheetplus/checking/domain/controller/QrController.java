package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.QrcodeRequestDto;
import sheetplus.checking.domain.dto.QrcodeResponseDto;
import sheetplus.checking.domain.service.QrcodeService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/student")
public class QrController {

    private final QrcodeService qrcodeService;

    @PostMapping("/qrcode/check")
    public ResponseEntity<QrcodeResponseDto> qrcodeCheck(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody QrcodeRequestDto qrcodeRequestDto){

        QrcodeResponseDto qrcodeResponseDto
                = qrcodeService.createParticipation(
                        token.replace("Bearer","").trim(), qrcodeRequestDto);

        return ResponseEntity.ok()
                .body(qrcodeResponseDto);
    }


}
