package sheetplus.checkings.domain.entry.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class EntryResponseDto {

    private Long id;
    private String name;
    private String location;
    private String building;
    private String teamNumber;
    private String major;
    private String professorName;
    private String leaderName;
    private String entryType;

}
