package sheetplus.checkings.domain.eventSending.entity;


import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.enums.SendingStatus;
import sheetplus.checkings.domain.enums.SendingType;
import sheetplus.checkings.domain.event.entity.Event;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class EventSending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_sending_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private SendingStatus sendingStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private SendingType sendingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event eventSendingEvent;

    public void changeStatus(SendingStatus sendingStatus){
        this.sendingStatus = sendingStatus;
    }

}
