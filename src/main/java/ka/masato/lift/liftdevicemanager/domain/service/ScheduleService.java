package ka.masato.lift.liftdevicemanager.domain.service;

import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import com.microsoft.azure.sdk.iot.service.jobs.JobClient;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import ka.masato.lift.liftdevicemanager.domain.repository.ScheduleRepository;
import ka.masato.lift.liftdevicemanager.domain.service.exceptions.UserPermitRefferenceException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class ScheduleService {


    private final ScheduleRepository scheduleRepository;
    private final LiftsService liftService;
    private final JobClient jobClient;

    public ScheduleService(ScheduleRepository scheduleRepository, LiftsService liftService, JobClient jobClient) {
        this.scheduleRepository = scheduleRepository;
        this.liftService = liftService;

        this.jobClient = jobClient;
    }


    @Transactional
    public Schedule createSchedule(Integer deviceId, Schedule schedule) throws IOException, IotHubException {
        //TODO Reference deviceId.
        Lift lift = liftService.getLiftById(deviceId);
        schedule.setLift(lift);
        String jobId = UUID.randomUUID().toString();
        schedule.setScheduleId(jobId);
        Date nowTime = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowTime);
        calendar.add(Calendar.MINUTE, 5);
        Date startTime = calendar.getTime();
        System.out.println(nowTime.toString());
        System.out.println(startTime.toString());
        jobClient.scheduleDeviceMethod(jobId, "deviceId='" + deviceId + "'",
                "scheduledUPLift", 5L, 5L, null,
                startTime, 10);

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void cancelSchedule(Integer deviceId, String scheduleId) throws IOException, IotHubException {
        Schedule schedule = getSchedule(deviceId, scheduleId);
        jobClient.cancelJob(schedule.getScheduleId());
        scheduleRepository.delete(scheduleId);
    }

    @Transactional
    public void updateSchedule(Integer deviceId, String scheduleId, Schedule newSchedule) throws IOException, IotHubException {
        Schedule schedule = getSchedule(deviceId, scheduleId);
        newSchedule.setScheduleId(schedule.getScheduleId());
        if (newSchedule.getDate().compareTo(schedule.getDate()) != 0) {
            newSchedule.setDate(schedule.getDate());
            jobClient.cancelJob(newSchedule.getScheduleId());
            jobClient.scheduleDeviceMethod(newSchedule.getScheduleId(), "deviceId='" + deviceId + "'",
                    "scheduledUPLift", 5L, 5L, null,
                    new Date(), 10);
        }

        scheduleRepository.save(newSchedule);
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or (returnObject.getLift().user == principal.username)")
    public Schedule getSchedule(Integer deviceId, String scheduleId) {
        Lift lift = liftService.getLiftById(deviceId);
        Schedule schedule = scheduleRepository.getOne(scheduleId);
        if (schedule.getLift().getLiftId() != lift.getLiftId()) {
            throw new UserPermitRefferenceException();
        }
        return schedule;
    }

}
