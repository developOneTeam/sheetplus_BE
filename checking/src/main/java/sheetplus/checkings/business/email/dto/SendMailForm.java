package sheetplus.checkings.business.email.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String html;
}