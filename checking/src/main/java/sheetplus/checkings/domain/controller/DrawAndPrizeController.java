package sheetplus.checkings.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.dto.DrawEventRequestDto;
import sheetplus.checkings.domain.dto.DrawEventResponseDto;
import sheetplus.checkings.domain.dto.DrawUpdateRequestDto;
import sheetplus.checkings.domain.dto.DrawUpdateResponseDto;
import sheetplus.checkings.domain.service.PrizeAndDrawEventService;
import sheetplus.checkings.domain.dto.PrizeConditionRequestDto;
import sheetplus.checkings.response.Api;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DrawAndPrizeController {

    private final PrizeAndDrawEventService prizeAndDrawEventService;

    @PostMapping("private/admin/prize/target/refresh")
    public void eventTargetRefresh(
            @RequestBody PrizeConditionRequestDto prizeConditionRequestDto) {
        prizeAndDrawEventService.participateStateRefresh(prizeConditionRequestDto.getCondition());
        // 조회 기능 추가 필요

    }

    @PostMapping("private/admin/draw/create")
    public Api<DrawEventResponseDto> createDraw(
            @RequestBody DrawEventRequestDto drawEventRequestDto
    ){
        return Api.CREATED(prizeAndDrawEventService.createDrawMember(drawEventRequestDto));
    }

    @PatchMapping("private/admin/draw/receive/update")
    public Api<DrawUpdateResponseDto> updateDrawEventReceiveCondition(
            @RequestBody DrawUpdateRequestDto drawUpdateRequestDto
            ){
        
        return Api.UPDATED(prizeAndDrawEventService.updateDrawReceived(drawUpdateRequestDto));
    }
    
    @DeleteMapping("private/admin/draw/{draw}/delete")
    public Api<String> deleteDrawEventReceiveCondition(
            @PathVariable(name = "draw") Long id
    ){
        prizeAndDrawEventService.deleteDraw(id);
        return Api.DELETE("삭제 완료");
    }




}
