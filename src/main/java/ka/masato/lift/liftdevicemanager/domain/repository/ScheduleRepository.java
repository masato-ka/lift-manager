package ka.masato.lift.liftdevicemanager.domain.repository;

import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    //    @Query("SELECT s FROM schedule as s WHERE s.status <> :statusName")
//    List<Schedule> findByStatus(@Param("statusName")String status);
    public List<Schedule> findByStatusNot(String status);

    public List<Schedule> findTop5ByLiftOrderByDateDesc(Lift lift);

    public List<Schedule> findByLift(Lift lift);
    public List<Schedule> findByStatus(String status);
}
