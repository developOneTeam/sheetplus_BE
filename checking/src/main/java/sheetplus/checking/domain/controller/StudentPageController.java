package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.EventResponseDto;
import sheetplus.checking.domain.dto.StudentHomePageResponseDto;
import sheetplus.checking.domain.dto.StudentPageActivitiesResponseDto;
import sheetplus.checking.domain.service.StudentPageService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentPageController {

    private final StudentPageService studentPageService;


    @GetMapping("private/student/{contest}/home")
    public ResponseEntity<StudentHomePageResponseDto> readStudentHome(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");

        return ResponseEntity.ok()
                .body(studentPageService.readStudentHomePage(token, contestId));
    }

    @GetMapping("public/{contest}/schedule")
    public ResponseEntity<List<EventResponseDto>> readStudentSchedule(
            @PathVariable("contest") Long contestId){

        return ResponseEntity.ok()
                .body(studentPageService.readStudentSchedulePage(contestId));
    }

    @GetMapping("private/student/{contest}/activities")
    public ResponseEntity<StudentPageActivitiesResponseDto> readStudentActivities(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");

        return ResponseEntity.ok()
                .body(studentPageService.readStudentActivitiesPage(token, contestId));
    }


}
