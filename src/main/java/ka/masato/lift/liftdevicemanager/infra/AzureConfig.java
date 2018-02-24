package ka.masato.lift.liftdevicemanager.infra;

import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwin;
import com.microsoft.azure.sdk.iot.service.jobs.JobClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AzureConfig {

    private String registryConnection = "HostName=bean.azure-devices.net;SharedAccessKeyName=iothubowner;SharedAccessKey=dp+mSWL0IVWhUgVZop3dY/2+RWWARSsPbAjqVzLVL+s=";
    private String deviceTwinConnection = "HostName=bean.azure-devices.net;SharedAccessKeyName=iothubowner;SharedAccessKey=dp+mSWL0IVWhUgVZop3dY/2+RWWARSsPbAjqVzLVL+s=";

    @Bean
    RegistryManager getRegistryManager() throws IOException {
        return RegistryManager.createFromConnectionString(registryConnection);
    }

    @Bean
    DeviceTwin getDeviceTwin() throws IOException {
        return DeviceTwin.createFromConnectionString(deviceTwinConnection);
    }

    @Bean
    JobClient getJobClient() throws IOException {
        return JobClient.createFromConnectionString(registryConnection);
    }
}
