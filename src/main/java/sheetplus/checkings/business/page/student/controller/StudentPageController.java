package sheetplus.checkings.business.page.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.ActivitiesResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomePageResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentPageActivitiesResponseDto;
import sheetplus.checkings.business.page.student.service.StudentPageService;
import sheetplus.checkings.exception.error.ErrorResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("private/student")
@Tag(name = "Student-Page", description = "StudentPage Service API")
public class StudentPageController {

    private final StudentPageService studentPageService;

    @GetMapping("/contests/{contest}/home/v1")
    @Operation(summary = "Student-Page Home GET", description = "학생 페이지 Home 화면 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "학생 페이지 Home 화면 데이터 조회 성공",
                    content = @Content(schema =
                    @Schema(implementation = StudentHomePageResponseDto.class),
                            mediaType = "application/json"),
                    headers = {@Header(name = "etag",
                    description = "\"etagexample\"과 같은 형태로 제공됩니다. If-None-Match속성에 Etag를 추가해서 요청하세요"),
                    @Header(name = "Cache-Control",
                            description = "클라이언트 캐시 사용, 캐싱 최대유효시간 1시간, 유효시간 지난 후에는 반드시 서버로 재요청하세요")}),
            @ApiResponse(responseCode = "304", description = "캐시 데이터의 변경사항이 없습니다. 로컬 캐시 데이터를 사용하세요",
                    content = @Content (mediaType = "None")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Member]정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
    })
    public ResponseEntity<StudentHomePageResponseDto> readStudentHome(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token,

            @Parameter(description = "Contest PK", example = "1")
            @PathVariable("contest") Long contestId){

        token = token.replace("Bearer ", "");

        return ResponseEntity.ok(studentPageService.readStudentHomePage(token, contestId));
    }



    @GetMapping("/contests/{contest}/activities/v1")
    @Operation(summary = "Student-Page activities GET", description = "학생 페이지 Activity 화면 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "학생 페이지 Activity 화면 데이터 조회 성공",
                    content = @Content(schema =
                    @Schema(implementation = StudentPageActivitiesResponseDto.class),
                            mediaType = "application/json"),
                    headers = {@Header(name = "etag",
                            description = "\"etagexample\"과 같은 형태로 제공됩니다. If-None-Match속성에 Etag를 추가해서 요청하세요"),
                            @Header(name = "Cache-Control",
                                    description = "클라이언트 캐시 사용, 캐싱 최대유효시간 1시간, 유효시간 지난 후에는 반드시 서버로 재요청하세요")}),
            @ApiResponse(responseCode = "304", description = "캐시 데이터의 변경사항이 없습니다. 로컬 캐시 데이터를 사용하세요)",
                    content = @Content (mediaType = "None")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Participate] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
    })
    public ResponseEntity<StudentPageActivitiesResponseDto> readStudentActivities(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token,

            @Parameter(description = "Contest PK", example = "1")
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
