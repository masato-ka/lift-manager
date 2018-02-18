package ka.masato.lift.liftdevicemanager.application;

import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import ka.masato.lift.liftdevicemanager.domain.service.LiftsService;
import ka.masato.lift.liftdevicemanager.domain.service.ScheduleService;
import ka.masato.lift.liftdevicemanager.security.login.PrincipalUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/devices/{id}/schedules")
    public List<Schedule> getAllSchedule(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer deviceId){
        Lift lift = liftsService.getLiftById(user.getUser(), deviceId);
        List<Schedule> schedules = lift.getSchedules();
        return schedules;
    }

    @GetMapping(value="/devices/{id}/schedules/{scheduleId}")
    public Schedule getOneSchedule(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer deviceId,
                                   @PathVariable Integer scheduleId){
        Schedule schedule= scheduleService.getSchedule(user.getUser(), deviceId, scheduleId);
        return schedule;
    }

    @PostMapping("/devices/{id}/schedules")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Schedule createNewSchedule(@AuthenticationPrincipal PrincipalUser user,
                                            @PathVariable Integer deviceId, @RequestBody Schedule schedule){
        Lift lift = liftsService.getLiftById(user.getUser(), deviceId);
        schedule.setLift(lift);
        return scheduleService.createSchedule(schedule);
    }




}
