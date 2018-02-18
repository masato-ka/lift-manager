package ka.masato.lift.liftdevicemanager.domain.service;

import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwin;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.model.LiftUser;
import ka.masato.lift.liftdevicemanager.domain.repository.LiftsRepository;
import ka.masato.lift.liftdevicemanager.domain.repository.LiftUserRepository;
import ka.masato.lift.liftdevicemanager.domain.service.exceptions.UserPermitRefferenceException;
import ka.masato.lift.liftdevicemanager.infra.soracom.SoracomApiManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
        Lift result = null;
        String devicePrimaryKey = "";
        lift.setUser(user);
        //if(registryManager.getDevice(lift.getDeviceId()) != null){
            Device preDevice = Device.createFromId(lift.getDeviceId(), null,null);
            Device device = registryManager.addDevice(preDevice);
            devicePrimaryKey = device.getPrimaryKey();
            lift.setUser(user);
            result = liftsRepository.save(lift);
        //}else{
        //    throw new DeviceAlreadyExsistsOnIoTHub();
        //}

        return result;
    }

    @Transactional
    public void deleteLift(LiftUser user, Integer id) throws IOException, IotHubException {

        Lift lift = liftsRepository.findOne(id);
        if(user.getUserId().equals(lift.getUser().getUserId())){
            liftsRepository.delete(id);
            registryManager.removeDevice(lift.getDeviceId());
        }else{
            throw new UserPermitRefferenceException();
        }

        return;
    }


    public Lift updateLift(LiftUser user, Lift lift ){
        Lift result = liftsRepository.save(lift);
        return result;
    }

    public List<Lift> getAllLifts(LiftUser user){
        List<Lift> lifts = liftsRepository.findByUser(user);
        return lifts;
    }

    public Lift getLiftById(LiftUser user, Integer id) {
        Lift result = liftsRepository.findOne(id);
        LiftUser deviceHasUser = result.getUser();
        if(!user.getUserId().equals(deviceHasUser.getUserId())){
            throw new UserPermitRefferenceException();
        }
        return result;
    }

}
