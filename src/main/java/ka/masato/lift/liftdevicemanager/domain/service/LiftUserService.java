package ka.masato.lift.liftdevicemanager.domain.service;

import ka.masato.lift.liftdevicemanager.domain.repository.LiftUserRepository;
import org.springframework.stereotype.Service;

@Service
public class LiftUserService {

    private final LiftUserRepository liftUserRepository;


    public LiftUserService(LiftUserRepository liftUserRepository) {
        this.liftUserRepository = liftUserRepository;
    }
}
