package sheetplus.checkings.domain.participatecontest.repository;

import org.springframework.data.domain.Pageable;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.domain.participatecontest.dto.ParticipateContestDto.ParticipateInfoResponseDto;

import java.util.List;

public interface ParticipateContestStateRepositoryCustom {

    void targetUpdates(int condition);
    ParticipateInfoResponseDto participateContestCounts(Long id);
    List<MemberInfoResponseDto> drawMemberInfoRead(Long contestId, Pageable pageable);


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    List<MemberInfoResponseDto> participateMemberInfoRead(Long contestId);

}
