package ka.masato.lift.liftdevicemanager.domain.service;

import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import ka.masato.lift.liftdevicemanager.domain.repository.ScheduleRepository;
import ka.masato.lift.liftdevicemanager.domain.service.exceptions.UserPermitRefferenceException;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LiftsService liftService;

    public ScheduleService(ScheduleRepository scheduleRepository, LiftsService liftService) {
        this.scheduleRepository = scheduleRepository;
        this.liftService = liftService;
    }

    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(LiftUser user, Integer deviceId, Integer scheduleId) {
        Lift lift = liftService.getLiftById(user, deviceId);
        Schedule schedule = scheduleRepository.getOne(scheduleId);
        if (schedule.getLift().getLiftId() != lift.getLiftId()) {
            throw new UserPermitRefferenceException();
        }
        return schedule;
    }
}
