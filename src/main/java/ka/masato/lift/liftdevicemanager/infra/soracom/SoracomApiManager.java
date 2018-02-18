package ka.masato.lift.liftdevicemanager.infra.soracom;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SoracomApiManager {

    private final RestTemplate restTemplate;

    public SoracomApiManager(RestTemplateBuilder restTemplateBuilder, RequestTokenInterceptor requestTokenInterceptor) {
        restTemplate = restTemplateBuilder.additionalInterceptors(requestTokenInterceptor).build();
    }


}

