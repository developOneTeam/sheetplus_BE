package sheetplus.checkings.domain.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntryType {


    PRELIMINARY("예선작"),
    FINALS("본선작");

    private String message;

}
