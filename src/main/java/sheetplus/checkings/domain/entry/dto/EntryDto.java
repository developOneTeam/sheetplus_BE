package sheetplus.checkings.domain.entry.dto;

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
        private String name;
        private String location;
        private String building;
        private String teamNumber;
        private String professorName;
        private String major;
        private String leaderName;
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
