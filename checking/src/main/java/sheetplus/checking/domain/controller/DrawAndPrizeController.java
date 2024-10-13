package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.DrawEventRequestDto;
import sheetplus.checking.domain.dto.DrawEventResponseDto;
import sheetplus.checking.domain.dto.DrawUpdateRequestDto;
import sheetplus.checking.domain.dto.DrawUpdateResponseDto;
import sheetplus.checking.domain.service.PrizeAndDrawEventService;
import sheetplus.checking.domain.service.PrizeConditionRequestDto;
import sheetplus.checking.response.Api;

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
