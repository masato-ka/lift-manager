package ka.masato.lift.liftdevicemanager.application;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ka.masato.lift.liftdevicemanager.domain.model.Event;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.service.EventsService;
import ka.masato.lift.liftdevicemanager.security.login.PrincipalUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class EventApi {

    private final EventsService eventsService;


    public EventApi(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @PostMapping(value = "/devices/{id}/events")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "デバイスへEventを新規に一件追加する。", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Eventの追加に成功した。"),
    }
    )
    public Event addNewEvent(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer id, @RequestBody Event event) {

        Event result = eventsService.createNewEvent(user.getUser(), id, event);
        return result;

    }

    @GetMapping(value = "/devices/{id}/events")
    @ApiOperation(value = "デバイスに追加されたイベントを追加する。", response = Lift.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "デバイスのイベントの取得に成功した。"),
    }
    )
    public List<Event> getAllEvent(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer id) {
        List<Event> result = eventsService.getDeviceEvent(user.getUser(), id);
        return result;
    }
}
