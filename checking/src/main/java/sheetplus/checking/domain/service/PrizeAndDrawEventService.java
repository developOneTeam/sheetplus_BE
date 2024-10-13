package sheetplus.checking.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.*;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Draw;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.repository.ContestRepository;
import sheetplus.checking.domain.repository.DrawRepository;
import sheetplus.checking.domain.repository.MemberRepository;
import sheetplus.checking.domain.repository.ParticipateContestStateRepository;


@RequiredArgsConstructor
@Service
@Slf4j
public class PrizeAndDrawEventService {

    private final ParticipateContestStateRepository participateContestStateRepository;
    private final DrawRepository drawRepository;
    private final MemberRepository memberRepository;
    private final ContestRepository contestRepository;

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
                .orElse(null);
        Contest contest = contestRepository.findById(drawEventRequestDto.getContestId())
                .orElse(null);
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
        Draw draw = drawRepository.findById(drawUpdateRequestDto.getDrawId()).orElse(null);
        draw.setReceiveCons(draw.getReceiveCons());

        return DrawUpdateResponseDto.builder()
                .prizeType(draw.getDrawType())
                .receiveConditionMessage(draw.getReceiveCons().getMessage())
                .build();
    }

    @Transactional
    public void deleteDraw(Long id){
        drawRepository.deleteById(id);
    }


}
