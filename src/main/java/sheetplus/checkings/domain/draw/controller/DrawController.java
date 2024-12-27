package sheetplus.checkings.domain.draw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.draw.dto.request.DrawEventRequestDto;
import sheetplus.checkings.domain.draw.dto.response.DrawEventResponseDto;
import sheetplus.checkings.domain.draw.dto.request.DrawUpdateRequestDto;
import sheetplus.checkings.domain.draw.dto.response.DrawUpdateResponseDto;
import sheetplus.checkings.domain.draw.service.DrawEventService;
import sheetplus.checkings.deprecated.prize.PrizeConditionRequestDto;
import sheetplus.checkings.util.response.Api;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/admin")
@Slf4j
public class DrawController {

    private final DrawEventService drawEventService;


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    //@PostMapping("private/admin/prize/target/refresh")
    public void eventTargetRefresh(
            @RequestBody PrizeConditionRequestDto prizeConditionRequestDto) {
        drawEventService.participateStateRefresh(prizeConditionRequestDto.getCondition());
        // 조회 기능 추가 필요

    }

    @PostMapping("/draw")
    public Api<DrawEventResponseDto> createDraw(
            @RequestBody DrawEventRequestDto drawEventRequestDto
    ){
        return Api.CREATED(drawEventService.createDrawMember(drawEventRequestDto));
    }

    @PatchMapping("/draw")
    public Api<DrawUpdateResponseDto> updateDrawEventReceiveCondition(
            @RequestBody DrawUpdateRequestDto drawUpdateRequestDto
            ){
        
        return Api.UPDATED(drawEventService.updateDrawReceived(drawUpdateRequestDto));
    }
    
    @DeleteMapping("/draw/{draw}")
    public Api<String> deleteDrawEventReceiveCondition(
            @PathVariable(name = "draw") Long id
    ){
        drawEventService.deleteDraw(id);
        return Api.DELETE("삭제 완료");
    }




}
