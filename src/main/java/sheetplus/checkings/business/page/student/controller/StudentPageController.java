package sheetplus.checkings.business.page.student.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.ActivitiesResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomePageResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentPageActivitiesResponseDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.business.page.student.service.StudentPageService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentPageController {

    private final StudentPageService studentPageService;


    @GetMapping("private/student/{contest}/home/v1")
    public ResponseEntity<StudentHomePageResponseDto> readStudentHome(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");

        return ResponseEntity.ok(studentPageService.readStudentHomePage(token, contestId));
    }

    @GetMapping("public/{contest}/schedule/v1")
    public ResponseEntity<List<EventResponseDto>> readStudentSchedule(
            @PathVariable("contest") Long contestId,
            @RequestParam(value = "offset", required = false)
            Integer offset,
            @RequestParam(value = "limit", required = false)
            Integer limit
    ){
        return ResponseEntity.ok(studentPageService
                .readStudentSchedulePage(contestId, PageRequest.of(offset-1, limit)));
    }

    @GetMapping("private/student/{contest}/activities/v1")
    public ResponseEntity<StudentPageActivitiesResponseDto> readStudentActivities(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");
        ActivitiesResponseDto activitiesResponseDto
                = studentPageService.readStudentActivitiesPage(token, contestId);


        return ResponseEntity.ok(StudentPageActivitiesResponseDto
                .builder()
                .activitiesResponseDto(activitiesResponseDto)
                .build());
    }


}
