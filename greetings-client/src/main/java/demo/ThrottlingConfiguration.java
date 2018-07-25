package demo;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThrottlingConfiguration {
    @Bean
    RateLimiter rateLimiter() {
        return RateLimiter.create(1.0 / 10.0);
    }
}
