package sheetplus.checkings.domain.entry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import sheetplus.checkings.domain.enums.EntryType;

import java.util.List;

@Getter
@NoArgsConstructor
public class EntryDto {


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EntryRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String name;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String location;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String building;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String teamNumber;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String professorName;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String major;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String leaderName;
        @NotNull(message = "null은 허용하지 않습니다.")
        private EntryType entryType;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EntryInfoResponseDto{
        private Long majorCounts;
        private Long preliminaryCounts;
        private Long finalCounts;
        private Long totalCounts;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EntryResponseDto{
        private Long id;
        private String name;
        private String location;
        private String building;
        private String teamNumber;
        private String major;
        private String professorName;
        private String leaderName;
        private String entryType;
        private List<Link> link;

    }


}
