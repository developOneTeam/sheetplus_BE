package sheetplus.checkings.domain.entry.dto.request;

import lombok.*;
import sheetplus.checkings.domain.enums.EntryType;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EntryRequestDto {

    private String name;
    private String location;
    private String building;
    private String teamNumber;
    private String professorName;
    private String major;
    private String leaderName;
    private EntryType entryType;

}
