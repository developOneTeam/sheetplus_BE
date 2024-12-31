package sheetplus.checkings.domain.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import sheetplus.checkings.domain.enums.ContestCons;
import sheetplus.checkings.domain.enums.EventCategory;
import sheetplus.checkings.domain.enums.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class EventDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EventRequestDto{
        private String name;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endTime;
        private String location;
        private String building;
        private String speakerName;
        private String major;
        private ContestCons condition;
        private EventType eventType;
        private EventCategory category;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EventResponseDto{
        private Long id;
        private String name;
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
        private List<Link> link;

    }

}
