package sheetplus.checkings.exception.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import sheetplus.checkings.util.response.Result;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorResponse {

    private Result result;

    public static ErrorResponse ERROR(HttpStatus errorStatus, String errorMessage) {
        return new ErrorResponse(Result.ERROR(errorStatus, errorMessage));
    }

}
