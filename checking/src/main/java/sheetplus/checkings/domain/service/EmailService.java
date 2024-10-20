package sheetplus.checkings.domain.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.entity.TemporaryMember;
import sheetplus.checkings.domain.repository.TemporaryMemberRepository;
import sheetplus.checkings.exception.ApiException;
import sheetplus.checkings.util.MailUtil;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import static sheetplus.checkings.error.ApiError.TEMPORARY_NOT_FOUND;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final SesClient sesClient;
    private final MailUtil mailUtil;

    @Value("${email.subject}")
    private String SUBJECT;
    @Value("${email.mail-html}")
    private String MAIL_HTML;
    @Value("${email.email-domain}")
    private String EMAIL_DOMAIN;
    @Value("${email.sender-email}")
    private String SENDER_EMAIL;
    @Value("${email.encode-type}")
    private String ENCODE_TYPE;

    private final String SEPARATOR = "@";

    public String sendMail(String toEmail) {

        String code = mailUtil.createCodeUtil();

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(Destination.builder()
                        .toAddresses(toEmail)
                        .build())
                .message(Message.builder()
                        .subject(Content.builder()
                                .data(SUBJECT)
                                .charset(ENCODE_TYPE)
                                .build())
                        .body(Body.builder()
                                .html(Content.builder()
                                        .data(mailUtil
                                                .setContextUtil(toEmail, code
                                                        , MAIL_HTML))
                                        .charset(ENCODE_TYPE)
                                        .build())
                                .build())
                        .build())
                .source(SENDER_EMAIL)
                .build();

        sesClient.sendEmail(request);
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
    public void deleteTemporaryMember(String email){
        temporaryMemberRepository.deleteById(email);
    }


    public boolean verifyEmail(String checkEmail, String code) {
        TemporaryMember temporaryMember =
                temporaryMemberRepository.findById(checkEmail)
                        .orElseThrow(() -> new ApiException(TEMPORARY_NOT_FOUND));

        if(!temporaryMember.getCode().equals(code)){
           throw new ApiException(TEMPORARY_NOT_FOUND);
        }

        return true;
    }

    public boolean verifyEmailDomain(String email){
        String emailDomain = email.
                substring(email
                        .lastIndexOf(SEPARATOR)+1);

        if(!emailDomain.equals(EMAIL_DOMAIN)){
            return false;
        }

        return true;
    }

}
