package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checking.domain.dto.EmailCheckRequestDto;
import sheetplus.checking.domain.dto.EmailRequestDto;
import sheetplus.checking.domain.service.EmailService;


/**
 * ResponseEntity에 대한 공통 양식은 이후 개발 예정
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/public")
public class MailController {

    private final EmailService emailService;

    @PostMapping("/mail/auth")
    public ResponseEntity<String> sendMail(
            @RequestBody EmailRequestDto emailRequestDto){
        if(!emailService.verifyEmailDomain(emailRequestDto.getReceiver())){
            return ResponseEntity.badRequest()
                    .body("학교 이메일로 요청해주세요");
        }

        emailService.createTemporaryMember(emailRequestDto.getReceiver(),
                emailService.sendMail(emailRequestDto.getReceiver()));
        return ResponseEntity.ok()
                .body("전송 완료");
    }

    @PostMapping("/mail/auth/check")
    public ResponseEntity<String> checkMail(
            @RequestBody EmailCheckRequestDto emailCheckRequestDto){

        if(!emailService.verifyEmail(emailCheckRequestDto.getEmail()
                , emailCheckRequestDto.getCode())){

            return ResponseEntity.badRequest()
                    .body("인증 실패");
        }

        emailService.deleteTemporaryMember(emailCheckRequestDto.getEmail());
        return ResponseEntity.ok()
                .body("인증 성공");
    }

}
