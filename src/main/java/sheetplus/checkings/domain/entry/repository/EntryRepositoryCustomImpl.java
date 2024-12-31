package sheetplus.checkings.domain.entry.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryInfoResponseDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryResponseDto;
import sheetplus.checkings.domain.entry.entity.Entry;
import sheetplus.checkings.domain.enums.EntryType;

import java.util.List;

import static sheetplus.checkings.domain.entry.entity.QEntry.entry;


@RequiredArgsConstructor
@Slf4j
public class EntryRepositoryCustomImpl implements EntryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public EntryInfoResponseDto entryInfoCounts() {
        return EntryInfoResponseDto.builder()
                .majorCounts(
                        queryFactory
                                .select(entry.major.countDistinct())
                                .from(entry)
                                .fetchFirst()
                )
                .preliminaryCounts(
                        queryFactory.select(entry.count())
                                .from(entry)
                                .where(entry.entryType.eq(EntryType.PRELIMINARY))
                                .fetchFirst()
                )
                .finalCounts(
                        queryFactory.select(entry.count())
                                .from(entry)
                                .where(entry.entryType.eq(EntryType.FINALS))
                                .fetchFirst()
                )
                .totalCounts(
                        queryFactory.select(entry.count())
                                .from(entry)
                                .fetchFirst()
                )
                .build();
    }


    @Override
    public List<EntryResponseDto> getEntry() {
        List<Entry> entries
                = queryFactory.selectFrom(entry)
                .orderBy(entry.name.asc())
                .limit(14)
                .fetch();


        return entries.stream()
                .map(p -> EntryResponseDto.builder()
                        .id(p.getId())
                        .entryType(p.getEntryType().getMessage())
                        .professorName(p.getProfessorName())
                        .teamNumber(p.getTeamNumber())
                        .leaderName(p.getLeaderName())
                        .location(p.getLocation())
                        .building(p.getBuilding())
                        .name(p.getName())
                        .build())
                .toList();
    }
}
