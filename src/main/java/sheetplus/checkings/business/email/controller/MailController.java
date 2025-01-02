package sheetplus.checkings.business.email.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.business.email.dto.EmailDto.EmailRequestDto;
import sheetplus.checkings.business.email.service.EmailService;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("public")
public class MailController {

    private final EmailService emailService;

    @PostMapping("/auth/mails/v1")
    public ResponseEntity<Void> sendMail(
            @RequestBody EmailRequestDto emailRequestDto){
        emailService.verifyEmailDomain(emailRequestDto.getReceiver());
        String code = emailService.createCode();

        emailService.sendEmail(emailRequestDto.getReceiver(),
                emailService.registerCheck(emailRequestDto.getReceiver()), code);

        emailService.createTemporaryMember(emailRequestDto.getReceiver(),
                code);
        return ResponseEntity.accepted().build();
    }

}
