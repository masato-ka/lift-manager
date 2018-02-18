package ka.masato.lift.liftdevicemanager.application;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
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
    @ApiOperation(value = "ユーザ情報を返す。", response = LiftUser.class)
    @ApiResponses(value = {
    }
    )
    public LiftUser getUser(@AuthenticationPrincipal PrincipalUser user){
        return user.getUser();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "ユーザ情報を更新する。", response = Iterable.class)
    @ApiResponses(value = {
    }
    )
    public LiftUser updateUser(@AuthenticationPrincipal PrincipalUser user, @RequestBody LiftUser liftUser){
        LiftUser result = null;
        return result;
    }

}
