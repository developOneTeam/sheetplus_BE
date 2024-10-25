package sheetplus.checkings.domain.entry.repository;

import sheetplus.checkings.domain.entry.dto.response.EntryInfoResponseDto;
import sheetplus.checkings.domain.entry.dto.response.EntryResponseDto;

import java.util.List;

public interface EntryRepositoryCustom {

    EntryInfoResponseDto entryInfoCounts();

    List<EntryResponseDto> getEntry();

}
