package sheetplus.checkings.domain.adminacceptcons.entity;


import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.enums.AcceptCons;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class AdminAcceptCons {

    @Id
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcceptCons acceptCons;


    public void updateAcceptCons(AcceptCons acceptCons){
        this.acceptCons = acceptCons;
    }

}
