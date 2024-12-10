package sheetplus.checkings.domain.favorite.dto.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteRequestDto {

    private Long contestId;
    private Long eventId;
    private String deviceToken;

}
