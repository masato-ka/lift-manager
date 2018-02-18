package ka.masato.lift.liftdevicemanager.infra.soracom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestTokenInterceptor implements ClientHttpRequestInterceptor{


    @Value("${soracom.api.key}")
    private String soracomApiKey;
    @Value("${soracom.api.token}")
    private String soracomToken;


    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        httpRequest.getHeaders().add("X-Soracom-API-Key", soracomApiKey);
        httpRequest.getHeaders().add("X-Soracom-Token", soracomToken);

        ClientHttpResponse response = null;

        response = clientHttpRequestExecution.execute(httpRequest, bytes);

        return response;
    }
}
