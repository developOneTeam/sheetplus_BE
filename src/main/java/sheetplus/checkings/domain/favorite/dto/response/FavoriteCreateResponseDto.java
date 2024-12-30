package sheetplus.checkings.domain.favorite.dto.response;

import lombok.*;
import org.springframework.hateoas.Link;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteCreateResponseDto {

    private Long favoriteId;
    private String studentId;
    private String contestName;
    private String eventName;
    private List<Link> links;

}
