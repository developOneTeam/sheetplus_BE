package sheetplus.checkings.domain.repository;

import sheetplus.checkings.domain.dto.MemberInfoDto;
import sheetplus.checkings.domain.dto.ParticipateInfoDto;

import java.util.List;

public interface ParticipateContestStateRepositoryCustom {

    void targetUpdates(int condition);
    ParticipateInfoDto participateContestCounts(Long id);
    List<MemberInfoDto> drawMemberInfoRead(Long contestId);


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    List<MemberInfoDto> participateMemberInfoRead(Long contestId);

}
