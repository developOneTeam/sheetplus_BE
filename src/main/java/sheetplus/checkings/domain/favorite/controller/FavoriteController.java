package sheetplus.checkings.domain.favorite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteRequestDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteCreateResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.SubScribeResponseDTO;
import sheetplus.checkings.domain.favorite.service.FavoriteCRUDService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/student")
public class FavoriteController implements FavoriteControllerSpec{

    private final FavoriteCRUDService favoriteCRUDService;

    @PostMapping("/favorite/v1")
    public ResponseEntity<FavoriteCreateResponseDto> createFavorite(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody @Validated FavoriteRequestDto favoriteRequestDto) {

        FavoriteCreateResponseDto favoriteCreateResponseDto =
                favoriteCRUDService.createFavorite(token.replace("Bearer ", "")
                ,favoriteRequestDto);

        return ResponseEntity.accepted()
                .body(favoriteCreateResponseDto);
    }


    @GetMapping("/contests/{contest}/favorites/v1")
    public ResponseEntity<List<FavoriteResponseDto>> getFavorites(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "contest") Long contestId,
            @RequestParam(value = "offset", required = false)
            Integer offset,
            @RequestParam(value = "limit", required = false)
            Integer limit
            ){
        List<FavoriteResponseDto> favorites
                = favoriteCRUDService.getFavorites(token.replace("Bearer ", "")
                , contestId, PageRequest.of(offset-1, limit));

        return ResponseEntity.ok(favorites);
    }


    @DeleteMapping("/favorites/{favorite}/devices/{deviceToken}/v1")
    public ResponseEntity<Void> deleteFavorite(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "favorite") Long id,
            @PathVariable(name = "deviceToken") String deviceToken
    ){
        favoriteCRUDService.deleteFavorites(id, token.replace("Bearer ", ""), deviceToken);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/favorites/{favorite}/status/{statusType}/v1")
    public ResponseEntity<SubScribeResponseDTO> favoriteStatusCheck(
            @PathVariable(name = "favorite") Long id,
            @PathVariable(name = "statusType") String type
    ){
        SubScribeResponseDTO subScribeResponseDTO = favoriteCRUDService.getFavoriteStatus(id, type);

        return ResponseEntity.ok(subScribeResponseDTO);
    }

}
