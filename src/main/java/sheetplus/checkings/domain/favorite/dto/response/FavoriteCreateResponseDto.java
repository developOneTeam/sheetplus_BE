package sheetplus.checkings.domain.favorite.dto.response;

import lombok.*;

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

}
