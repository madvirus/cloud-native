package demo;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableRetry
@EnableCircuitBreaker
@EnableHystrixDashboard
public class DemoApplication {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Bean
//    @Primary
//    @Order(value= Ordered.HIGHEST_PRECEDENCE)
//    public HystrixCommandAspect hystrixAspect() {
//        return new HystrixCommandAspect();
//    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

@RestController
class ClientController {

    @Autowired
    CBGreetingClient cbClient;

    @Autowired
    private RetryableGreetingClient retryClient;

    @GetMapping("/client/retry/{name}")
    public String retry(@PathVariable String name) {
        return retryClient.greet(name);
    }

    @GetMapping("/client/cb1/{name}")
    public String cb1(@PathVariable String name) {
        return cbClient.greet1(name);
    }

    @GetMapping("/client/cb2/{name}")
    public String cb2(@PathVariable String name) {
        return cbClient.greet2(name);
    }

//    @GetMapping("/hystrix/metrics")
//    public List<String> hystrixMetrics() {
//        List<String> cbs = new ArrayList<>();
//        for (HystrixCommandMetrics metrics : HystrixCommandMetrics.getInstances()) {
//            HystrixCircuitBreaker circuitBreaker = HystrixCircuitBreaker.Factory
//                    .getInstance(metrics.getCommandKey());
//            if (circuitBreaker != null) {
//                cbs.add(metrics.getCommandGroup().name() + "::"
//                        + metrics.getCommandKey().name() + "::"
//                        + circuitBreaker.isOpen());
//            }
//        }
//        return cbs;
//    }
}
