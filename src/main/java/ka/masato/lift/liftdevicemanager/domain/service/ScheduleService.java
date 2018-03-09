package ka.masato.lift.liftdevicemanager.domain.service;

import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubTooManyRequestsException;
import com.microsoft.azure.sdk.iot.service.jobs.JobClient;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import ka.masato.lift.liftdevicemanager.domain.repository.ScheduleRepository;
import ka.masato.lift.liftdevicemanager.domain.service.exceptions.UserPermitRefferenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
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

        Date startDateTime = exchangeLocalDateTimeToDate(schedule.getDate());

        String jobId = UUID.randomUUID().toString();
        schedule.setScheduleId(jobId);
        log.debug("JobID:" + jobId + "\t" + "start:" + startDateTime.toString());
        //TODO Handling Exception IotHubTooManyRequestsException
        try {
            jobClient.scheduleDeviceMethod(jobId, "deviceId='" + lift.getImsi() + "'",
                    schedule.getApi(), 10L, 10L, null,
                    startDateTime, 10);
        } catch (IotHubTooManyRequestsException exception) {
            log.error("The number of active job is max at IoT Hub.");
        }
        return scheduleRepository.save(schedule);
    }

    private Date exchangeLocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zone);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    @Transactional
    public void cancelSchedule(Integer deviceId, String scheduleId) throws IOException, IotHubException {
        Schedule schedule = getSchedule(deviceId, scheduleId);
        jobClient.cancelJob(schedule.getScheduleId());
        scheduleRepository.delete(scheduleId);
    }

    @Transactional
    public Schedule updateSchedule(Integer deviceId, String scheduleId, Schedule newSchedule) throws IOException, IotHubException {
        Schedule schedule = getSchedule(deviceId, scheduleId);
        newSchedule.setScheduleId(schedule.getScheduleId());
        //TODO アップデート変更処理を再度実装すること。
        if (newSchedule.getDate().compareTo(schedule.getDate()) != 0) {
            newSchedule.setDate(schedule.getDate());
            Date startDateTime = exchangeLocalDateTimeToDate(newSchedule.getDate());
            jobClient.cancelJob(newSchedule.getScheduleId());
            jobClient.scheduleDeviceMethod(newSchedule.getScheduleId(), "deviceId='" + deviceId + "'",
                    newSchedule.getApi(), 5L, 5L, null,
                    startDateTime, 10);
        }

        return scheduleRepository.save(newSchedule);
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

    public List<Schedule> getSchduleDoYet() {
        List<Schedule> schedules = scheduleRepository.findByStatusNot("completed");
        return schedules;
    }

    public void updateStatus(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Schedule> getAllPendingStatus() {
        return scheduleRepository.findByStatus("pending");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #lift.user.userId == principal.username")
    public List<Schedule> getSchdules(Lift lift) {
        List<Schedule> schedules = scheduleRepository.findTop5ByLiftOrderByDateAsc(lift);
        return schedules;
    }
}
