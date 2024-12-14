package sheetplus.checkings.domain.eventSending.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.enums.SendingStatus;
import sheetplus.checkings.domain.enums.SendingType;
import sheetplus.checkings.domain.eventSending.entity.EventSending;

import java.util.List;

public interface EventSendingRepository extends JpaRepository<EventSending, Long> {

    List<EventSending> findByEventSendingEvent_Id(Long eventId);
    EventSending findByEventSendingEvent_IdAndSendingType(Long eventId, SendingType sendingType);
    List<EventSending> findBySendingStatusNot(SendingStatus sendingStatus);
    void deleteByEventSendingEvent_Id(Long eventId);
}
