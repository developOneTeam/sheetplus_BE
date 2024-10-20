package sheetplus.checkings.domain.repository;

import sheetplus.checkings.domain.dto.MemberInfoDto;
import sheetplus.checkings.domain.dto.ParticipateInfoDto;

import java.util.List;

public interface ParticipateContestStateRepositoryCustom {

    void targetUpdates(int condition);
    ParticipateInfoDto participateContestCounts(Long id);
    List<MemberInfoDto> participateMemberInfoRead(Long contestId);
    List<MemberInfoDto> drawMemberInfoRead(Long contestId);
}
