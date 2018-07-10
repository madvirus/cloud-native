package env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("some.properties")
public class Application {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    static PropertySourcesPlaceholderConfigurer pspc() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${configuration.projectName}")
    private String fieldValue;

    @Value("${JAVA_HOME}")
    private String javaHome;

    @Autowired
    void setEnv(Environment env) {
        logger.info("========== env:java.version: " + env.getProperty("java.version"));
        logger.info("========== env:JAVA_HOME: " + env.getProperty("JAVA_HOME"));
    }

    @PostConstruct
    void afterPropertiesSet() {
        logger.info("========== fieldValue: {}", fieldValue);
        logger.info("========== JAVA_HOME: {}", javaHome);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("prod");
        ctx.register(Application.class);
        ctx.refresh();
    }
}
