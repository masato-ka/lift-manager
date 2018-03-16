package ka.masato.lift.liftdevicemanager.domain.service;

import ka.masato.lift.liftdevicemanager.domain.model.Event;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.repository.EventsRepository;
import ka.masato.lift.liftdevicemanager.domain.repository.LiftsRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventsService {

    private final EventsRepository eventsRepository;
    private final LiftsRepository liftsService;

    public EventsService(EventsRepository eventsRepository, LiftsRepository liftsService) {
        this.eventsRepository = eventsRepository;
        this.liftsService = liftsService;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or #lift.user.userId == principal.username")
    public Event createNewEvent(Lift lift, Event event) {
        event.setLift(lift);
        Event result = eventsRepository.save(event);
        return result;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #lift.user.userId == principal.username")
    public List<Event> getDeviceEvent(Lift lift) {
        List<Event> result = null;
        result = eventsRepository.findTop5ByLiftOrderByEventTimeDesc(lift);
        return result;
    }

}
