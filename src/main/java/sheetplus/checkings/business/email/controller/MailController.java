package sheetplus.checkings.business.email.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.business.email.dto.EmailRequestDto;
import sheetplus.checkings.business.email.service.EmailService;
import sheetplus.checkings.util.response.Api;


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
                emailService.sendEmail(emailRequestDto.getReceiver(),
                        emailService.registerCheck(emailRequestDto.getReceiver())));
        return Api.OK("전송 완료");
    }



}
