package sheetplus.checkings.business.page.student.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.ActivitiesResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomeEventsInfoResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomeMemberAndStampInfoResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentPageActivitiesResponseDto;
import sheetplus.checkings.business.page.student.service.StudentPageService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("private/student")
public class StudentPageController implements StudentPageControllerSpec{

    private final StudentPageService studentPageService;

    @GetMapping("/contests/{contest}/homes/stamp-info/v2")
    public ResponseEntity<StudentHomeMemberAndStampInfoResponseDto> readStudentHomeMemberWithStampInfo(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");

        return ResponseEntity.ok(studentPageService.readStudentHomeMemberAndStampInfo(token, contestId));
    }

    @GetMapping("/contests/{contest}/homes/event-info/v2")
    public ResponseEntity<StudentHomeEventsInfoResponseDto> readStudentHomeEventInfo(
            @PathVariable("contest") Long contestId,
            @RequestParam(value = "building") String building
            ){

        return ResponseEntity.ok(studentPageService
                .readStudentHomeEventInfo(contestId, building));
    }


    @GetMapping("/contests/{contest}/activities/v1")
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
