package sheetplus.checkings.domain.repository;

import sheetplus.checkings.domain.dto.EntryInfoDto;
import sheetplus.checkings.domain.dto.EntryResponseDto;

import java.util.List;

public interface EntryRepositoryCustom {

    EntryInfoDto entryInfoCounts();

    List<EntryResponseDto> getEntry();

}
