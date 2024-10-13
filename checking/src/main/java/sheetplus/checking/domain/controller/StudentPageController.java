package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.EventResponseDto;
import sheetplus.checking.domain.dto.StudentHomePageResponseDto;
import sheetplus.checking.domain.dto.StudentPageActivitiesResponseDto;
import sheetplus.checking.domain.service.StudentPageService;
import sheetplus.checking.response.Api;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentPageController {

    private final StudentPageService studentPageService;


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

        return Api.READ(studentPageService.readStudentActivitiesPage(token, contestId));
    }


}
