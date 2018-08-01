package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Component
public class RetryableGreetingClient {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;
    private final String serviceUri;

    @Autowired
    public RetryableGreetingClient(RestTemplate restTemplate,
                                   @Value("${greeting-service.uri}") String serviceUri) {
        this.restTemplate = restTemplate;
        this.serviceUri = serviceUri;
    }

    @Retryable(include = Exception.class, maxAttempts = 4, backoff = @Backoff(multiplier = 2))
    public String greet(String name) {
        logger.info("attempting to call the greeting-service-ch12 " + new Date());

        ParameterizedTypeReference<Map<String, String>> ptr =
                new ParameterizedTypeReference<Map<String, String>>() {};

        return restTemplate.exchange(serviceUri + "/hi/" + name, HttpMethod.GET, null, ptr, name)
                .getBody()
                .get("greeting");
    }
}
