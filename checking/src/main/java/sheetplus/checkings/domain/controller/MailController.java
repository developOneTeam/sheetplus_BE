package sheetplus.checkings.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.domain.dto.EmailCheckRequestDto;
import sheetplus.checkings.domain.dto.EmailRequestDto;
import sheetplus.checkings.domain.service.EmailService;
import sheetplus.checkings.response.Api;


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
    public Api<Object> sendMail(
            @RequestBody EmailRequestDto emailRequestDto){
        emailService.verifyEmailDomain(emailRequestDto.getReceiver());

        emailService.createTemporaryMember(emailRequestDto.getReceiver(),
                emailService.sendMail(emailRequestDto.getReceiver()));
        return Api.OK("전송 완료");
    }

    @PostMapping("/mail/auth/check")
    public Api<Object> checkMail(
            @RequestBody EmailCheckRequestDto emailCheckRequestDto){
        emailService.verifyEmail(emailCheckRequestDto.getEmail()
                , emailCheckRequestDto.getCode());


        return Api.OK("인증 성공");
    }

}
