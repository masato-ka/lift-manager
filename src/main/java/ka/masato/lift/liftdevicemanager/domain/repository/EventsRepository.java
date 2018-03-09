package ka.masato.lift.liftdevicemanager.domain.repository;

import ka.masato.lift.liftdevicemanager.domain.model.Event;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Integer> {

    public List<Event> findTop5ByLiftOrderByEventTimeAsc(Lift lift);
}
