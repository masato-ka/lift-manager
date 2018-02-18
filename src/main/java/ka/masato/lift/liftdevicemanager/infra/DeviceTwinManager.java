package ka.masato.lift.liftdevicemanager.infra;

import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwin;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwinDevice;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DeviceTwinManager {

    private final RegistryManager registryManager;
    private final DeviceTwin deviceTwin;


    public DeviceTwinManager(RegistryManager registryManager, DeviceTwin deviceTwin) {
        this.registryManager = registryManager;
        this.deviceTwin = deviceTwin;
    }

    public Device createDevice(Device device) throws IOException, IotHubException {

        registryManager.addDevice(device);
        return registryManager.getDevice(device.getDeviceId());
    }

    public Device getDevice(String deviceId) throws IOException, IotHubException {
        return registryManager.getDevice(deviceId);
    }

    public List<Device> getDevices() throws IOException, IotHubException {
        return registryManager.getDevices(10);
    }

    /*public Set<> DegetDeviceTwinTags(Device device){
            DeviceTwinDevice deviceTwinDevice = new DeviceTwinDevice(device.getDeviceId());

    }*/
}
