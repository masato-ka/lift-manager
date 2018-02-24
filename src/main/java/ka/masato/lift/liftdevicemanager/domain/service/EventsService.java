package ka.masato.lift.liftdevicemanager.domain.service;

import ka.masato.lift.liftdevicemanager.domain.model.Event;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import ka.masato.lift.liftdevicemanager.domain.repository.EventsRepository;
import ka.masato.lift.liftdevicemanager.domain.repository.LiftsRepository;
import ka.masato.lift.liftdevicemanager.domain.service.exceptions.UserPermitRefferenceException;
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
    public Event createNewEvent(LiftUser user, Integer liftId, Event event) {
        Lift lift = liftsService.findOne(liftId);
        if (!lift.getUser().getUserId().equals(user.getUserId())) {
            throw new UserPermitRefferenceException();
        }
        event.setLift(lift);
        Event result = eventsRepository.save(event);
        return result;
    }

    public List<Event> getDeviceEvent(LiftUser user, Integer liftId) {
        List<Event> result = null;
        Lift lift = liftsService.findOne(liftId);
        if (!lift.getUser().getUserId().equals(user.getUserId())) {
            throw new UserPermitRefferenceException();
        }
        result = lift.getEvents();
        return result;
    }

}
