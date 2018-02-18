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
    private final LiftsRepository liftsRepository;

    public EventsService(EventsRepository eventsRepository, LiftsRepository liftsRepository) {
        this.eventsRepository = eventsRepository;
        this.liftsRepository = liftsRepository;
    }

    @Transactional
    public Event createNewEvent(LiftUser user, Integer liftId, Event event) {
        Lift lift = liftsRepository.findOne(liftId);
        if (!lift.getUser().getUserId().equals(user.getUserId())) {
            throw new UserPermitRefferenceException();
        }
        event.setLift(lift);
        Event result = eventsRepository.save(event);
        return result;
    }

    public List<Event> getDeviceEvent(LiftUser user, Integer liftId) {
        List<Event> result = null;
        Lift lift = liftsRepository.findOne(liftId);
        if (!lift.getUser().getUserId().equals(user.getUserId())) {
            throw new UserPermitRefferenceException();
        }
        result = lift.getEvents();
        return result;
    }

}
