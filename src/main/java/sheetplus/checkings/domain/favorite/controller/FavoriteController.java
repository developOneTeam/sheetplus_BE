package sheetplus.checkings.domain.favorite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.favorite.dto.request.FavoriteRequestDto;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteCreateResponseDto;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteResponseDto;
import sheetplus.checkings.domain.favorite.service.FavoriteCRUDService;
import sheetplus.checkings.util.response.Api;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/")
public class FavoriteController {

    private final FavoriteCRUDService favoriteCRUDService;

    @PostMapping("favorite/create")
    public Api<FavoriteCreateResponseDto> createFavorite(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody FavoriteRequestDto favoriteRequestDto) {

        FavoriteCreateResponseDto favoriteCreateResponseDto =
                favoriteCRUDService.createFavorite(token.replace("Bearer ", "")
                ,favoriteRequestDto);

        return Api.CREATED(favoriteCreateResponseDto);
    }


    @GetMapping("{contest}/event/favorites")
    public Api<List<FavoriteResponseDto>> getFavorites(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "contest") Long contestId){
        List<FavoriteResponseDto> favorites
                = favoriteCRUDService.getFavorites(token.replace("Bearer ", ""), contestId);

        return Api.READ(favorites);
    }


    @DeleteMapping("favorite/{favorite}/delete")
    public Api<String> deleteFavorite(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "favorite") Long id
    ){
        favoriteCRUDService.deleteFavorites(id, token.replace("Bearer ", ""));
        return Api.DELETE("삭제 완료");
    }


}
