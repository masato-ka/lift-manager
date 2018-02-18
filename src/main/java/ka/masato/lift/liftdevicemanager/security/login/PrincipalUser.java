package ka.masato.lift.liftdevicemanager.security.login;

import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import lombok.Data;
import org.springframework.security.core.authority.AuthorityUtils;

@Data
public class PrincipalUser extends org.springframework.security.core.userdetails.User {

    private final LiftUser user;

    public PrincipalUser(LiftUser user){
        super(user.getUserId(),user.getEncodedPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.user = user;
    }

}
