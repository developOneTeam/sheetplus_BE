package sheetplus.checkings.business.page.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomeEventsInfoResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomeMemberAndStampInfoResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentPageActivitiesResponseDto;
import sheetplus.checkings.exception.error.ErrorResponse;

@Tag(name = "Student-Page", description = "StudentPage Service API")
public interface StudentPageControllerSpec {


    @Operation(summary = "학생 Home 페이지 학생 정보 및 참여한 스탬프 개수 GET", description = "학생 Home 페이지 학생 정보 및 참여한 스탬프 개수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "학생 Home 페이지 학생 정보 및 참여한 스탬프 개수 데이터 조회 성공",
                    content = @Content(schema =
                    @Schema(implementation = StudentHomeMemberAndStampInfoResponseDto.class),
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
    ResponseEntity<StudentHomeMemberAndStampInfoResponseDto> readStudentHomeMemberWithStampInfo(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            String token,
            @Parameter(description = "Contest PK", example = "1")
            Long contestId
    );

    @Operation(summary = "학생 Home페이지 오늘 참여할 수 있는 이벤트 GET", description = "학생 Home페이지 오늘 참여할 수 있는 이벤트 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "학생 Home페이지 오늘 참여할 수 있는 이벤트 데이터 조회 성공",
                    content = @Content(schema =
                    @Schema(implementation = StudentHomeEventsInfoResponseDto.class),
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
    ResponseEntity<StudentHomeEventsInfoResponseDto> readStudentHomeEventInfo(
            @Parameter(description = "Contest PK", example = "1")
            @PathVariable("contest") Long contestId,
            @Parameter(description = "탐색할 이벤트 건물", example = "인문과학관")
            @RequestParam(value = "building") String building);


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
    ResponseEntity<StudentPageActivitiesResponseDto> readStudentActivities(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            String token,
            @Parameter(description = "Contest PK", example = "1")
            Long contestId
    );

}
