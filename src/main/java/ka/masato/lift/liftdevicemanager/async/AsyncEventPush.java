package ka.masato.lift.liftdevicemanager.async;

import ka.masato.lift.liftdevicemanager.domain.model.Event;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.service.EventsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Component
@Async
public class AsyncEventPush {

    private final EventsService eventsService;

    public AsyncEventPush(EventsService eventsService) {
        this.eventsService = eventsService;

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #lift.user.userId == principal.username")
    public void eventPush(SseEmitter sseEmitter, Lift lift) throws IOException, InterruptedException {
        List<Event> events = eventsService.getDeviceEvent(lift);
        for (Event event : events) {
            sseEmitter.send("test");
        }
        sseEmitter.complete();
    }


}
