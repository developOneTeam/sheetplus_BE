package sheetplus.checkings.util.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@Builder
public class Result {

    private HttpStatus resultStatus;
    private String resultMessage;

    public static Result ERROR(HttpStatus httpStatus, String message){
        return Result.builder()
                .resultStatus(httpStatus)
                .resultMessage(message)
                .build();
    }



}