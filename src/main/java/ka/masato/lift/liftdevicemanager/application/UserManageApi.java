package ka.masato.lift.liftdevicemanager.application;

import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import ka.masato.lift.liftdevicemanager.domain.service.LiftUserService;
import ka.masato.lift.liftdevicemanager.security.login.PrincipalUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserManageApi {

    private final LiftUserService liftUserService;

    public UserManageApi(LiftUserService liftUserService) {
        this.liftUserService = liftUserService;
    }

    @GetMapping("/{id}")
    public LiftUser getUser(@AuthenticationPrincipal PrincipalUser user){
        return user.getUser();
    }

    @PutMapping("/{id}")
    public LiftUser updateUser(@AuthenticationPrincipal PrincipalUser user, @RequestBody LiftUser liftUser){
        LiftUser result = null;
        return result;
    }

}
