package demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CBGreetingClient {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;
    private final String serviceUri;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value="10000"), // 10초 동안
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="6"), // 6개 이상 요청
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="30"), // 30% 이상 실패
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="20000"), // 20초 동안 서킷 오픈
    })
    public String greet1(String name) {
        logger.info("HYSTRIX01 : call the greeting-service-ch12");

        ParameterizedTypeReference<Map<String, String>> ptr =
                new ParameterizedTypeReference<Map<String, String>>() {};

        return restTemplate.exchange(serviceUri + "/hi/" + name, HttpMethod.GET, null, ptr, name)
                .getBody()
                .get("greeting");
    }

    @Autowired
    public CBGreetingClient(RestTemplate restTemplate,
                            @Value("${greeting-service.uri}") String serviceUri) {
        this.restTemplate = restTemplate;
        this.serviceUri = serviceUri;
    }

    public String fallback(String name) {
        return "AN-NYOUNG " + name;
    }

    @HystrixCommand()
    public String greet2(String name) {
        logger.info("HYSTRIX02 : call the greeting-service-ch12");

        ParameterizedTypeReference<Map<String, String>> ptr =
                new ParameterizedTypeReference<Map<String, String>>() {};

        return restTemplate.exchange(serviceUri + "/hi/" + name, HttpMethod.GET, null, ptr, name)
                .getBody()
                .get("greeting");
    }
}
