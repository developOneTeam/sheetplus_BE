package sheetplus.checkings.business.qrcode.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.qrcode.dto.QrcodeRequestDto;
import sheetplus.checkings.business.qrcode.dto.QrcodeResponseDto;
import sheetplus.checkings.business.qrcode.service.QrcodeService;
import sheetplus.checkings.util.response.Api;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/student")
public class QrController {

    private final QrcodeService qrcodeService;

    @PostMapping("/qrcode/check")
    public Api<QrcodeResponseDto> qrcodeCheck(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody QrcodeRequestDto qrcodeRequestDto){

        QrcodeResponseDto qrcodeResponseDto
                = qrcodeService.createParticipation(
                        token.replace("Bearer","").trim(), qrcodeRequestDto);

        return Api.OK(qrcodeResponseDto);
    }


}
