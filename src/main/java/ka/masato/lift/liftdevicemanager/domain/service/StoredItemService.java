package ka.masato.lift.liftdevicemanager.domain.service;

import ka.masato.lift.liftdevicemanager.domain.model.StoredItem;
import org.springframework.stereotype.Service;

@Service
public class StoredItemService {

    private final ka.masato.lift.liftdevicemanager.domain.repository.StoredItemRepository storedItemRepository;


    public StoredItemService(ka.masato.lift.liftdevicemanager.domain.repository.StoredItemRepository storedItemRepository) {
        this.storedItemRepository = storedItemRepository;
    }

    public StoredItem saveStoredItem(StoredItem storedItem){
        return storedItemRepository.save(storedItem);
    }

    public void deleteStoredItem(Integer itemId) {
        storedItemRepository.delete(itemId);
    }
}
