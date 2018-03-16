package ka.masato.lift.liftdevicemanager.application;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ka.masato.lift.liftdevicemanager.domain.model.Event;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.service.EventsService;
import ka.masato.lift.liftdevicemanager.domain.service.LiftsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class EventApi {

    private final EventsService eventsService;
    private final LiftsService liftsService;

    public EventApi(EventsService eventsService, LiftsService liftsService) {
        this.eventsService = eventsService;
        this.liftsService = liftsService;
    }

    @PostMapping(value = "/devices/{liftId}/events")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "デバイスへEventを新規に一件追加する。", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Eventの追加に成功した。"),
    }
    )
    public Event addNewEvent(@PathVariable Integer liftId, @RequestBody Event event) {
        Lift lift = liftsService.getLiftById(liftId);
        Event result = eventsService.createNewEvent(lift, event);
        return result;

    }

    @GetMapping(value = "/devices/{liftId}/events")
    @ApiOperation(value = "デバイスに追加されたイベントを追加する。", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "デバイスのイベントの取得に成功した。"),
    }
    )
    public List<Event> getAllEvent(@PathVariable Integer liftId) {
        Lift lift = liftsService.getLiftById(liftId);
        List<Event> result = eventsService.getDeviceEvent(lift);
        return result;
    }
}
