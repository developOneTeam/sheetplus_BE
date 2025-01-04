package sheetplus.checkings.domain.draw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawEventRequestDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawEventResponseDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawUpdateRequestDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawUpdateResponseDto;
import sheetplus.checkings.domain.draw.service.DrawEventService;
import sheetplus.checkings.deprecated.prize.PrizeConditionRequestDto;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/admin")
@Slf4j
public class DrawController implements DrawControllerSpec{

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

    @PostMapping("/draw/v1")
    public ResponseEntity<DrawEventResponseDto> createDraw(
            @RequestBody @Validated DrawEventRequestDto drawEventRequestDto
    ){
        return ResponseEntity.created(URI.create(""))
                .body(drawEventService.createDrawMember(drawEventRequestDto));
    }

    @PutMapping("/draw/v1")
    public ResponseEntity<DrawUpdateResponseDto> updateDrawEventReceiveCondition(
            @RequestBody @Validated DrawUpdateRequestDto drawUpdateRequestDto
            ){
        
        return ResponseEntity.ok(drawEventService.updateDrawReceived(drawUpdateRequestDto));
    }
    
    @DeleteMapping("/draw/{draw}/v1")
    public ResponseEntity<Void> deleteDrawEventReceiveCondition(
            @PathVariable(name = "draw") Long id
    ){
        drawEventService.deleteDraw(id);
        return ResponseEntity.noContent().build();
    }




}
