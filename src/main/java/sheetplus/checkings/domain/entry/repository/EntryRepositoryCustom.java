package sheetplus.checkings.domain.entry.repository;

import sheetplus.checkings.domain.entry.dto.EntryDto.EntryInfoResponseDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryResponseDto;

import java.util.List;

public interface EntryRepositoryCustom {

    EntryInfoResponseDto entryInfoCounts();

    List<EntryResponseDto> getEntry();

}
