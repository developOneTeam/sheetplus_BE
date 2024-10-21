package sheetplus.checkings.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.entity.enums.ValidCons;

@Entity(name = "temporary_member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class TemporaryMember {

    @Id
    private String email;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValidCons validCons;

    public void updateValidCOns(ValidCons validCons){
        this.validCons = validCons;
    }

}
