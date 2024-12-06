package sheetplus.checkings.business.page.student.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.page.student.dto.ActivitiesResponseDto;
import sheetplus.checkings.domain.event.dto.response.EventResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentHomePageResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageActivitiesResponseDto;
import sheetplus.checkings.business.page.student.service.StudentPageService;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteResponseDto;
import sheetplus.checkings.domain.favorite.service.FavoriteCRUDService;
import sheetplus.checkings.util.response.Api;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentPageController {

    private final StudentPageService studentPageService;
    private final FavoriteCRUDService favoriteCRUDService;


    @GetMapping("private/student/{contest}/home")
    public Api<StudentHomePageResponseDto> readStudentHome(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");

        return Api.READ(studentPageService.readStudentHomePage(token, contestId));
    }

    @GetMapping("public/{contest}/schedule")
    public Api<List<EventResponseDto>> readStudentSchedule(
            @PathVariable("contest") Long contestId){

        return Api.READ(studentPageService.readStudentSchedulePage(contestId));
    }

    @GetMapping("private/student/{contest}/activities")
    public Api<StudentPageActivitiesResponseDto> readStudentActivities(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");
        ActivitiesResponseDto activitiesResponseDto
                = studentPageService.readStudentActivitiesPage(token, contestId);
        List<FavoriteResponseDto> favoriteResponseDto
                = favoriteCRUDService.getFavorites(token, contestId);


        return Api.READ(StudentPageActivitiesResponseDto
                .builder()
                .activitiesResponseDto(activitiesResponseDto)
                .favorites(favoriteResponseDto)
                .build());
    }


}
