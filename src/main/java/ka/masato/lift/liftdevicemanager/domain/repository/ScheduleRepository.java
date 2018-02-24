package ka.masato.lift.liftdevicemanager.domain.repository;

import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import org.hibernate.boot.model.source.spi.JpaCallbackSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {

}
