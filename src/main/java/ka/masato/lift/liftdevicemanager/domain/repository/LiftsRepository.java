package ka.masato.lift.liftdevicemanager.domain.repository;

import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiftsRepository extends JpaRepository<Lift, Integer>{

    public Lift findByDeviceId(String deviceName);

    public List<Lift> findByUser(LiftUser userId);
}
