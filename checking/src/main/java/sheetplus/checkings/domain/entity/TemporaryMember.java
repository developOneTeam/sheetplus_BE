package sheetplus.checkings.domain.entity;

import jakarta.persistence.*;
import lombok.*;

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

}
