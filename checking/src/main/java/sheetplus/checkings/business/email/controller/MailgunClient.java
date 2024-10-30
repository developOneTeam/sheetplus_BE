package sheetplus.checkings.business.email.controller;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import sheetplus.checkings.business.email.dto.SendMailForm;

@FeignClient(name = "mailgun", url="${mailgun.base-url}")
@Qualifier(value = "mailgun")
public interface MailgunClient {

    @PostMapping("${mailgun.messages-url}")
    ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);


}
