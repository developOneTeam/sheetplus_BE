package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checking.domain.dto.EmailCheckRequestDto;
import sheetplus.checking.domain.dto.EmailRequestDto;
import sheetplus.checking.domain.service.EmailService;
import sheetplus.checking.response.Api;


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
        if(!emailService.verifyEmailDomain(emailRequestDto.getReceiver())){
            return Api.NOT_VALID(403,"학교 이메일로 요청해주세요");
        }

        emailService.createTemporaryMember(emailRequestDto.getReceiver(),
                emailService.sendMail(emailRequestDto.getReceiver()));
        return Api.OK("전송 완료");
    }

    @PostMapping("/mail/auth/check")
    public Api<Object> checkMail(
            @RequestBody EmailCheckRequestDto emailCheckRequestDto){

        if(!emailService.verifyEmail(emailCheckRequestDto.getEmail()
                , emailCheckRequestDto.getCode())){

            return Api.NOT_VALID(403,"인증 실패");
        }

        emailService.deleteTemporaryMember(emailCheckRequestDto.getEmail());
        return Api.OK("인증 성공");
    }

}
