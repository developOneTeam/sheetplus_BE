package sheetplus.checkings.domain.entry.dto.response;

import lombok.*;
import org.springframework.hateoas.Link;

import java.util.List;

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
    private List<Link> link;

}
