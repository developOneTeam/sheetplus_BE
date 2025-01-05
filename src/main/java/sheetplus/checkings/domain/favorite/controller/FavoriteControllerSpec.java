package sheetplus.checkings.domain.favorite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteCreateResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteRequestDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteResponseDto;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.util.List;

@Tag(name = "Favorite", description = "Favorite Service API")
public interface FavoriteControllerSpec {

    @Operation(summary = "Favorite CREATE", description = "즐겨찾기 이벤트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "이벤트 즐겨찾기 생성 및 FCM 구독등록 진행",
                    content = @Content(schema = @Schema(implementation = FavoriteCreateResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Member or Contest or Event] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "이미 등록된 즐겨찾기 이벤트입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<FavoriteCreateResponseDto> createFavorite(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            String token,
            FavoriteRequestDto favoriteRequestDto);


    @Operation(summary = "Favorite GET", description = "즐겨찾기 이벤트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 즐겨찾기 조회 성공",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = FavoriteResponseDto.class)),
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
            @ApiResponse(responseCode = "404", description = "구독 상태정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
    })
    ResponseEntity<List<FavoriteResponseDto>> getFavorites(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            String token,
            @Parameter(description = "Contest PK", example = "1")
            Long contestId,
            @Parameter(description = "조회할 페이지 번호", example = "1")
            Integer offset,
            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            Integer limit
    );


    @Operation(summary = "Favorite DELETE", description = "FCM 구독삭제 진행, 삭제 성공하면 이벤트 즐겨찾기 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "FCM 구독삭제 진행",
                    content = @Content(mediaType = "None")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Member or Event or Favorite or 구독 상태] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
    })
    ResponseEntity<Void> deleteFavorite(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            String token,
            @Parameter(description = "Favorite PK", example = "1")
            Long id,
            @Parameter(description = "DeviceToken - FCM에서 발급한 유저 디바이스 토큰입니다.",
                    example = "test123device4567token")
            String deviceToken
    );

    @Operation(summary = "Favorite STATUS", description = "FCM 구독작업의 상태를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FCM 구독작업 진행상태 조회 성공",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = FavoriteDto.SubScribeResponseDTO.class)),
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
            @ApiResponse(responseCode = "404", description = "[구독 상태] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
    })
    ResponseEntity<FavoriteDto.SubScribeResponseDTO> favoriteStatusCheck(
            @Parameter(description = "Favorite PK")
            Long id,
            @Parameter(description = "구독작업 유형 [subscribe or unsubscribe]")
            String type
    );

}
