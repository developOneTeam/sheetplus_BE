package sheetplus.checking.domain.repository;

import sheetplus.checking.domain.dto.MemberInfoDto;
import sheetplus.checking.domain.dto.ParticipateInfoDto;

import java.util.List;

public interface ParticipateContestStateRepositoryCustom {

    void targetUpdates(int condition);
    ParticipateInfoDto participateContestCounts(Long id);
    List<MemberInfoDto> participateMemberInfoRead(Long contestId);
    List<MemberInfoDto> drawMemberInfoRead(Long contestId);
}
