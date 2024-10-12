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
    public ResponseEntity<DrawEventResponseDto> createDraw(
            @RequestBody DrawEventRequestDto drawEventRequestDto
    ){
        return ResponseEntity.ok()
                .body(prizeAndDrawEventService.createDrawMember(drawEventRequestDto));
    }

    @PatchMapping("private/admin/draw/receive/update")
    public ResponseEntity<DrawUpdateResponseDto> updateDrawEventReceiveCondition(
            @RequestBody DrawUpdateRequestDto drawUpdateRequestDto
            ){
        
        return ResponseEntity.ok()
                .body(prizeAndDrawEventService.updateDrawReceived(drawUpdateRequestDto));
    }
    
    @DeleteMapping("private/admin/draw/{draw}/delete")
    public ResponseEntity<String> deleteDrawEventReceiveCondition(
            @PathVariable(name = "draw") Long id
    ){
        prizeAndDrawEventService.deleteDraw(id);
        return ResponseEntity.ok()
                .body("삭제 완료");
    }




}
