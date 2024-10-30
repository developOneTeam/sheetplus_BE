package sheetplus.checkings.business.email.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.email.controller.MailgunClient;
import sheetplus.checkings.business.email.dto.SendMailForm;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.temporarymember.entity.TemporaryMember;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.temporarymember.repository.TemporaryMemberRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.MailUtil;

import java.util.List;

import static sheetplus.checkings.exception.error.ApiError.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final MemberRepository memberRepository;
    private final MailUtil mailUtil;
    private final MailgunClient mailgunClient;

    @Value("${email.subject}")
    private String SUBJECT;
    @Value("${email.mail-register-html}")
    private String MAIL_REGISTER_HTML;
    @Value("${email.mail-login-html}")
    private String MAIL_LOGIN_HTML;
    @Value("${email.email-domain.list}")
    private List<String> EMAIL_DOMAIN;
    @Value("${email.sender-email}")
    private String SENDER_EMAIL;

    private final String SEPARATOR = "@";

    public String sendEmail(String toEmail, boolean registerCheck) {
        String code = mailUtil.createCodeUtil();

        SendMailForm sendMailForm = SendMailForm.builder()
                .subject(SUBJECT)
                .from(SENDER_EMAIL)
                .to(toEmail)
                .html(mailUtil
                        .setContextUtil(toEmail, code
                                , registerCheck ? MAIL_LOGIN_HTML
                                        : MAIL_REGISTER_HTML))
                .build();

        mailgunClient.sendEmail(sendMailForm);
        return code;
    }


    @Transactional
    public void createTemporaryMember(String email, String code){
        TemporaryMember temporaryMember = TemporaryMember
                .builder()
                .email(email)
                .code(code)
                .build();
        temporaryMemberRepository.save(temporaryMember);
    }

    @Transactional
    public void verifyEmail(String checkEmail, String code) {
        TemporaryMember temporaryMember =
                temporaryMemberRepository.findById(checkEmail)
                        .orElseThrow(() -> new ApiException(TEMPORARY_NOT_FOUND));

        if(!temporaryMember.getCode().equals(code)){
           throw new ApiException(TEMPORARY_NOT_VALID_CODE);
        }

    }

    public void verifyEmailDomain(String email){
        String emailDomain = email.
                substring(email
                        .lastIndexOf(SEPARATOR)+1);

        if(!EMAIL_DOMAIN.contains(emailDomain)){
            throw new ApiException(UNIVERSITY_EMAIL_NOT_VALID);
        }
    }

    @Transactional
    public boolean registerCheck(String email){
        Member members = memberRepository.findMemberByUniversityEmail(email)
                .orElse(null);

        return members != null;
    }

}
