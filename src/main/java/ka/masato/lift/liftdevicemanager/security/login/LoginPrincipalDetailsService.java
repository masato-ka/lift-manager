package ka.masato.lift.liftdevicemanager.security.login;

import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import ka.masato.lift.liftdevicemanager.domain.repository.LiftUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginPrincipalDetailsService implements UserDetailsService {

    private final LiftUserRepository liftUserRepository;

    public LoginPrincipalDetailsService(LiftUserRepository liftUserRepository) {
        this.liftUserRepository = liftUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LiftUser user = liftUserRepository.findOne(username);
        if (user == null) {
            throw new UsernameNotFoundException("The request user not found");
        }
        return new PrincipalUser(user);
    }
}
