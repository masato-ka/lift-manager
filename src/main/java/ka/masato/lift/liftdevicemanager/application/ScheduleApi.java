package ka.masato.lift.liftdevicemanager.application;

import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import ka.masato.lift.liftdevicemanager.domain.service.LiftsService;
import ka.masato.lift.liftdevicemanager.domain.service.ScheduleService;
import ka.masato.lift.liftdevicemanager.security.login.PrincipalUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ScheduleApi {

    private final ScheduleService scheduleService;
    private final LiftsService liftsService;


    public ScheduleApi(ScheduleService scheduleService, LiftsService liftsService) {
        this.scheduleService = scheduleService;
        this.liftsService = liftsService;
    }

    @GetMapping(value = "/devices/{deviceId}/schedules")
    public List<Schedule> getAllSchedule(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer deviceId){
        Lift lift = liftsService.getLiftById(deviceId);
        //List<Schedule> schedules = lift.getSchedules();
        List<Schedule> schedules = scheduleService.getSchdules(lift);
        return schedules;
    }

    @GetMapping(value = "/devices/{deviceId}/schedules/{scheduleId}")
    public Schedule getOneSchedule(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer deviceId,
                                   @PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getSchedule(deviceId, scheduleId);
        return schedule;
    }

    @PostMapping("/devices/{deviceId}/schedules")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Schedule createNewSchedule(@AuthenticationPrincipal PrincipalUser user,
                                      @PathVariable Integer deviceId, @RequestBody Schedule schedule) throws IOException, IotHubException {
        return scheduleService.createSchedule(deviceId, schedule);
    }


    @PutMapping("/devices/{deviceId}/schedules/{scheduleId}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Schedule updateSchedule(@PathVariable Integer deviceId, @PathVariable String scheduleId,
                                   @RequestBody Schedule schedule) throws IOException, IotHubException {
        return scheduleService.updateSchedule(deviceId, scheduleId, schedule);
    }

    @DeleteMapping("/devices/{deviceId}/schedules/{scheduleId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Integer deviceId, @PathVariable String scheduleId) throws IOException, IotHubException {
        scheduleService.cancelSchedule(deviceId, scheduleId);
    }


}
