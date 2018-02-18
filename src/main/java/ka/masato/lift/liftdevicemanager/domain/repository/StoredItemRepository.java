package ka.masato.lift.liftdevicemanager.domain.repository;

import ka.masato.lift.liftdevicemanager.domain.model.StoredItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredItemRepository extends JpaRepository<StoredItem, Integer>{
}
