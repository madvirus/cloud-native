package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class ConfigApplication {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RestController
    static class InfoController {
        private CudaProperties cudaProp;

        @Autowired
        public InfoController(CudaProperties cudaProp) {
            this.cudaProp = cudaProp;
        }

        @GetMapping("/info")
        public CudaProperties info() {
            return cudaProp;
        }
    }

    @RestController
    @RefreshScope
    static class TitleController {
        private String title;

        @Autowired
        public TitleController(@Value("${config.client.title}") String title) {
            System.out.println("*************** create " + this);
            this.title = title;
        }

        @GetMapping("/title")
        public String title() {
            return title;
        }

        @PreDestroy
        public void cleanup() {
            System.out.println("*************** clean up " + this);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
