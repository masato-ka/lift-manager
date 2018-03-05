package ka.masato.lift.liftdevicemanager.application;

import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import com.microsoft.azure.sdk.iot.service.jobs.JobClient;
import com.microsoft.azure.sdk.iot.service.jobs.JobResult;
import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import ka.masato.lift.liftdevicemanager.domain.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class MonitoringScheduledTask {

    private final ScheduleService scheduleService;
    private final JobClient jobClient;

    public MonitoringScheduledTask(ScheduleService scheduleService, JobClient jobClient) {
        this.scheduleService = scheduleService;
        this.jobClient = jobClient;
    }

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
    public void monitorSchedule() throws IOException, IotHubException {
        log.debug("monitor schedule");
        List<Schedule> schedules = scheduleService.getSchduleDoYet();

        for (Schedule schedule : schedules) {
            String scheduleId = schedule.getScheduleId();
            JobResult jobResult = jobClient.getJob(scheduleId);
            String status = jobResult.getJobStatus().toString();
            log.debug(status);

            if (!schedule.getStatus().equals(status)) {
                schedule.setStatus(status);
                scheduleService.updateStatus(schedule);
            }

        }


    }

}
