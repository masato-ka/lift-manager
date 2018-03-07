package ka.masato.lift.liftdevicemanager.domain.service;

import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwin;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import ka.masato.lift.liftdevicemanager.domain.repository.LiftUserRepository;
import ka.masato.lift.liftdevicemanager.domain.repository.LiftsRepository;
import ka.masato.lift.liftdevicemanager.infra.soracom.SoracomApiManager;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Service
public class LiftsService {

    private final LiftsRepository liftsRepository;
    private final LiftUserRepository liftUserRepository;
    private final RegistryManager registryManager;
    private final DeviceTwin deviceTwin;
    private final SoracomApiManager soracomApiManager;


    public LiftsService(LiftsRepository liftsRepository, LiftUserRepository liftUserRepository, RegistryManager registryManager,
                        DeviceTwin deviceTwin, SoracomApiManager soracomApiManager) {
        this.liftsRepository = liftsRepository;
        this.liftUserRepository = liftUserRepository;
        this.registryManager = registryManager;
        this.deviceTwin = deviceTwin;
        this.soracomApiManager = soracomApiManager;
    }

    @Transactional
    public Lift addNewLift(LiftUser user, Lift lift) throws IOException, IotHubException, NoSuchAlgorithmException {
        lift.setUser(user);
        Device preDevice = Device.createFromId(lift.getImsi(), null, null);
        Device device = registryManager.addDevice(preDevice);
        lift.setUser(user);
        //TODO ADD SORACOM Air sim enable.
        Lift result = liftsRepository.save(lift);
        return result;
    }

    @Transactional
    public void deleteLift(Integer id) throws IOException, IotHubException {
        Lift lift = new Lift();
        lift.setLiftId(id);
        Lift prevLift = getUpdateOrigin(lift);
        liftsRepository.delete(prevLift.getLiftId());
        registryManager.removeDevice(prevLift.getDeviceId());
        return;
    }

    @Transactional
    public Lift updateLift(Lift lift) {

        Lift origin = getUpdateOrigin(lift);
        origin.setDeviceId(lift.getDeviceId());
        origin.setImsi(lift.getImsi());
        Lift result = liftsRepository.save(origin);
        return result;
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or (#lift.user == principal.username)")
    private Lift getUpdateOrigin(Lift lift) {
        Lift origin = liftsRepository.findOne(lift.getLiftId());
        return origin;
    }

    public List<Lift> getAllLifts(LiftUser user){
        List<Lift> lifts = liftsRepository.findByUser(user);
        return lifts;
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or (returnObject.user.userId == principal.username)")
    public Lift getLiftByDeviceId(String deviceName) {
        Lift result = liftsRepository.findByDeviceId(deviceName);
        return result;
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or (returnObject.user.userId == principal.username)")
    public Lift getLiftById(Integer id) {
        Lift result = liftsRepository.findOne(id);
        return result;
    }

    //TODO nullが帰ってきた場合の処理
    @PostAuthorize("hasRole('ROLE_ADMIN') or (returnObject.user.userId == principal.username)")
    public Lift getLiftWithImsi(String imsi) {
        Lift result = liftsRepository.findByImsi(imsi);
        return result;
    }

}
