package sheetplus.checkings.domain.favorite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FavoriteDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FavoriteRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        private Long contestId;
        @NotNull(message = "null은 허용하지 않습니다.")
        private Long eventId;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String deviceToken;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FavoriteCreateResponseDto{
        private Long favoriteId;
        private String studentId;
        private String contestName;
        private String eventName;
        private List<Link> links;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FavoriteResponseDto{
        private Long favoriteId;
        private String eventName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endTime;
        private String location;
        private String building;
        private String speakerName;
        private String major;
        private String conditionMessage;
        private String eventTypeMessage;
        private String categoryMessage;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class SubScribeResponseDTO{
        private String statusType;
        private String statusMessage;
    }

}
