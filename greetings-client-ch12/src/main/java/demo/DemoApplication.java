package demo;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    //@Bean
    CommandLineRunner runner(RetryableGreetingClient client) {
        return args -> {
            try {
                String greet = client.greet("bkchoi");
                logger.info("============== " + greet);
            } catch (Exception e) {
                logger.error("error: ", e);
            }
        };
    }

//    @Bean
//    CommandLineRunner runner2(CBGreetingClient client) {
//        return args -> {
//            try {
//                String greet = client.greet("bkchoi");
//                logger.info("============== " + greet);
//            } catch (Exception e) {
//                logger.error("error: ", e);
//            }
//        };
//    }
}

@RestController
class ClientController {

    @Autowired
    CBGreetingClient cbClient;

    @GetMapping("/client2/{name}")
    public String greet2(@PathVariable String name) {
        return cbClient.greet2(name);
    }
}
