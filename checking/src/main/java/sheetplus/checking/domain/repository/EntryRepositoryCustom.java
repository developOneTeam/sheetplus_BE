package sheetplus.checking.domain.repository;

import sheetplus.checking.domain.dto.EntryInfoDto;
import sheetplus.checking.domain.dto.EntryResponseDto;

import java.util.List;

public interface EntryRepositoryCustom {

    EntryInfoDto entryInfoCounts();

    List<EntryResponseDto> getEntry();

}
