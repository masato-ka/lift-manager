package ka.masato.lift.liftdevicemanager.application;

import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.StoredItem;
import ka.masato.lift.liftdevicemanager.domain.service.LiftsService;
import ka.masato.lift.liftdevicemanager.domain.service.StoredItemService;
import ka.masato.lift.liftdevicemanager.security.login.PrincipalUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/devices")
@Api(value="Device management", description="デバイス管理系API")
public class DeviceManageApi {

    private final LiftsService liftsService;
    private final StoredItemService storedItemService;

    public DeviceManageApi(LiftsService liftsService, StoredItemService storedItemService) {
        this.liftsService = liftsService;
        this.storedItemService = storedItemService;
    }

    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED)
    @ApiOperation(value = "Liftを新規登録する。",response = Lift.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "新規デバイスの登録に成功した。"),
    }
    )
    public Lift registrationDevice(@RequestBody Lift lift, @AuthenticationPrincipal PrincipalUser user)
            throws IotHubException, NoSuchAlgorithmException, IOException {
        Lift result = liftsService.addNewLift(user.getUser(), lift);
        return result;
    }

    @GetMapping
    @ApiOperation(value = "ユーザの持つLiftの一覧を返す",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "リクエストが正常に処理された。"),
    }
    )
    public List<Lift> getDeviceList(@AuthenticationPrincipal PrincipalUser user){
        List<Lift> result = liftsService.getAllLifts(user.getUser());
        return result;
    }

    @GetMapping("deviceName/{deviceName}")
    @ApiOperation(value = "デバイス名のデバイスを返す", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "リクエストが正常に処理された。"),
    }
    )
    public Lift getLiftIdFromDevieName(@PathVariable String deviceName) {
        Lift lift = liftsService.getLiftByDeviceId(deviceName);
        return lift;

    }

    @GetMapping("imsi/{imsi}")
    @ApiOperation(value = "指定されたIMSIのデバイス情報を返す", response = Lift.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "リクエストが正常に処理された。")
    })
    public Lift getLiftWithImsi(@PathVariable String imsi) {
        Lift lift = liftsService.getLiftWithImsi(imsi);
        return lift;
    }

    @GetMapping(value="{id}")
    @ApiOperation(value = "指定されたIDのLiftを返す。",response = Lift.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "リクエストが正常に処理された。"),
    }
    )
    public Lift getDevice(@PathVariable Integer id) {
        Lift result = liftsService.getLiftById(id);
        return result;
    }

    @PutMapping(value="{id}")
    @ApiOperation(value = "Liftの登録情報を変更する。",response = Lift.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "リクエストが正常に処理された。"),
    }
    )
    public Lift updateDevice(@PathVariable Integer id, @RequestBody Lift lift) {
        lift.setLiftId(id);
        Lift result = liftsService.updateLift(lift);
        return result;
    }

    @DeleteMapping(value="{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    @ApiOperation(value = "指定されたIDのLiftを削除する。")
    @ApiResponses(value = {
            @ApiResponse(code = 203, message = "リクエストが正常に処理された。"),
    }
    )
    public void deleteDevice(@PathVariable Integer id)
            throws IOException, IotHubException {
        liftsService.deleteLift(id);
    }

    @GetMapping(value= "{id}/item")
    @ApiOperation(value = "指定されたIDのLiftに登録されたItemの値を取得する。",response = StoredItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "リクエストが正常に処理された。"),
    }
    )
    public StoredItem getStoredItem(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer id){
        Lift lift = liftsService.getLiftById(id);
        return lift.getThings();
    }

    @PostMapping(value="{id}/item")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "指定されたIDのLiftにItemを登録する。",response = StoredItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "リクエストが正常に処理された。"),
    }
    )
    public StoredItem createStoredItem(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer id,
                                       @RequestBody StoredItem storedItem){
        Lift lift = liftsService.getLiftById(id);
        storedItem.setLift(lift);
        StoredItem result = storedItemService.saveStoredItem(storedItem);
        return result;
    }

    @PutMapping(value="{id}/item")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Itemの値を更新する。",response = StoredItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "リクエストが正常に処理された。"),
    }
    )
    public StoredItem updateStoredItem(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer id,
                                       @RequestBody StoredItem storedItem){
        Lift lift = liftsService.getLiftById(id);
        storedItem.setLift(lift);
        StoredItem result = storedItemService.saveStoredItem(storedItem);
        return result;
    }

    @DeleteMapping(value = "{id}/item")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "指定されたIDのItemの値を削除する。",response = StoredItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 203, message = "リクエストが正常に処理された。"),
    }
    )
    public void deleteStoredItem(@AuthenticationPrincipal PrincipalUser user, @PathVariable Integer id){
        Lift lift = liftsService.getLiftById(id);
        StoredItem storedItem = lift.getThings();
        lift.setThings(null);
        storedItemService.deleteStoredItem(storedItem.getItemId());
    }

}
