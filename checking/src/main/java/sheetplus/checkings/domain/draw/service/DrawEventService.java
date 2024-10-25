package sheetplus.checkings.domain.draw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.draw.dto.request.DrawEventRequestDto;
import sheetplus.checkings.domain.draw.dto.request.DrawUpdateRequestDto;
import sheetplus.checkings.domain.draw.dto.response.DrawEventResponseDto;
import sheetplus.checkings.domain.draw.dto.response.DrawUpdateResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.draw.entity.Draw;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.draw.repository.DrawRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.participatecontest.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import static sheetplus.checkings.exception.error.ApiError.*;


@RequiredArgsConstructor
@Service
@Slf4j
public class DrawEventService {

    private final ParticipateContestStateRepository participateContestStateRepository;
    private final DrawRepository drawRepository;
    private final MemberRepository memberRepository;
    private final ContestRepository contestRepository;


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    @Transactional
    public void participateStateRefresh(int condition){
        participateContestStateRepository.targetUpdates(condition);
    }

    @Transactional
    public DrawEventResponseDto createDrawMember(DrawEventRequestDto drawEventRequestDto){
        Draw draw = Draw.builder()
                .drawType(drawEventRequestDto.getPrizeType())
                .receiveCons(drawEventRequestDto.getReceiveCons())
                .build();
        Member member = memberRepository.findById(drawEventRequestDto.getMemberId())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Contest contest = contestRepository.findById(drawEventRequestDto.getContestId())
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        draw.setMemberDraw(member);
        draw.setContestDraw(contest);
        Long id = drawRepository.save(draw).getId();

        return DrawEventResponseDto.builder()
                .drawId(id)
                .memberName(member.getName())
                .memberStudentId(member.getStudentId())
                .contestName(contest.getName())
                .prizeType(draw.getDrawType())
                .receiveConditionMessage(draw.getReceiveCons().getMessage())
                .build();
    }

    @Transactional
    public DrawUpdateResponseDto updateDrawReceived(DrawUpdateRequestDto drawUpdateRequestDto){
        Draw draw = drawRepository.findById(drawUpdateRequestDto.getDrawId())
                .orElseThrow(() -> new ApiException(DRAW_NOT_FOUND));
        draw.setReceiveCons(draw.getReceiveCons());

        return DrawUpdateResponseDto.builder()
                .prizeType(draw.getDrawType())
                .receiveConditionMessage(draw.getReceiveCons().getMessage())
                .build();
    }

    @Transactional
    public void deleteDraw(Long id){
        if (drawRepository.existsById(id)) {
            drawRepository.deleteById(id);
        }else{
            throw new ApiException(DRAW_NOT_FOUND);
        }

    }


}
