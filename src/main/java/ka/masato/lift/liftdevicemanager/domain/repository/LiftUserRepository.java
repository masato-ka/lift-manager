package ka.masato.lift.liftdevicemanager.domain.repository;


import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiftUserRepository extends JpaRepository<LiftUser, String> {
}
