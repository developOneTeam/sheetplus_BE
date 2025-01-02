package sheetplus.checkings.domain.favorite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.favorite.dto.request.FavoriteRequestDto;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteCreateResponseDto;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteResponseDto;
import sheetplus.checkings.domain.favorite.service.FavoriteCRUDService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/student")
public class FavoriteController {

    private final FavoriteCRUDService favoriteCRUDService;

    @PostMapping("/favorite/v1")
    public ResponseEntity<FavoriteCreateResponseDto> createFavorite(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody FavoriteRequestDto favoriteRequestDto) {

        FavoriteCreateResponseDto favoriteCreateResponseDto =
                favoriteCRUDService.createFavorite(token.replace("Bearer ", "")
                ,favoriteRequestDto);

        return ResponseEntity.accepted()
                .body(favoriteCreateResponseDto);
    }


    @GetMapping("/contests/{contest}/favorites/v1")
    public ResponseEntity<List<FavoriteResponseDto>> getFavorites(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "contest") Long contestId){
        List<FavoriteResponseDto> favorites
                = favoriteCRUDService.getFavorites(token.replace("Bearer ", ""), contestId);

        return ResponseEntity.ok(favorites);
    }


    @DeleteMapping("/favorites/{favorite}/devices/{deviceToken}/v1")
    public ResponseEntity<String> deleteFavorite(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "favorite") Long id,
            @PathVariable(name = "deviceToken") String deviceToken
    ){
        favoriteCRUDService.deleteFavorites(id, token.replace("Bearer ", ""), deviceToken);
        return ResponseEntity.accepted().build();
    }


}
