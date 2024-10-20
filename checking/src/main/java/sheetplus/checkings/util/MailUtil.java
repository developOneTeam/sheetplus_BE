package sheetplus.checkings.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtil {


    private final SpringTemplateEngine templateEngine;

    public String createCodeUtil(){
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(3);
            switch (index){
                case 0:
                    key.append((char)(random.nextInt(26) + 65));
                    break;
                case 1:
                    key.append((char)(random.nextInt(26) + 97));
                    break;
                case 2:
                    key.append((random.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    public String setContextUtil(String email, String code, String type){
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("email", email);
        return templateEngine.process(type, context);
    }


}
