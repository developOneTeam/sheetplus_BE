package sheetplus.checkings.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sheetplus.checkings.domain.dto.EntryInfoDto;
import sheetplus.checkings.domain.dto.EntryResponseDto;
import sheetplus.checkings.domain.entity.Entry;
import sheetplus.checkings.domain.entity.enums.EntryType;

import java.util.List;

import static sheetplus.checkings.domain.entity.QEntry.entry;

@RequiredArgsConstructor
@Slf4j
public class EntryRepositoryCustomImpl implements EntryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public EntryInfoDto entryInfoCounts() {
        return EntryInfoDto.builder()
                .majorCounts(
                        queryFactory
                                .select(entry.major.countDistinct())
                                .from(entry)
                                .fetchOne()
                )
                .preliminaryCounts(
                        queryFactory.select(entry.count())
                                .from(entry)
                                .where(entry.entryType.eq(EntryType.PRELIMINARY))
                                .fetchOne()
                )
                .finalCounts(
                        queryFactory.select(entry.count())
                                .from(entry)
                                .where(entry.entryType.eq(EntryType.FINALS))
                                .fetchOne()
                )
                .totalCounts(
                        queryFactory.select(entry.count())
                                .from(entry)
                                .fetchOne()
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
