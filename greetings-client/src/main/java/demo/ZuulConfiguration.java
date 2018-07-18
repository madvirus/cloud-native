package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableZuulProxy
public class ZuulConfiguration {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    CommandLineRunner commandLineRunner(RouteLocator locator) {
        return args -> {
            locator.getRoutes()
                    .forEach(r -> {
                        logger.info("{} ({}) {}", r.getId(), r.getLocation(), r.getFullPath());
                    });
        };
    }
}
