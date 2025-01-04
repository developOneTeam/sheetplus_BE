package sheetplus.checkings.util.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@Builder
@Schema(name="Error Result", description = "예외발생 메세지")
public class Result {

    @Schema(description = "응답 상태코드의 상태를 표시합니다", example = "BAD_REQUEST")
    private HttpStatus resultStatus;
    @Schema(description = "서버 내부적으로 개발한 예외 메세지를 출력합니다.", example = "잘못된 HTTP 입력 요청")
    private String resultMessage;

    public static Result ERROR(HttpStatus httpStatus, String message){
        return Result.builder()
                .resultStatus(httpStatus)
                .resultMessage(message)
                .build();
    }



}