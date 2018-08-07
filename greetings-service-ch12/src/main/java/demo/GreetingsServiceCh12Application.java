package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class GreetingsServiceCh12Application {

    public static void main(String[] args) {
        SpringApplication.run(GreetingsServiceCh12Application.class, args);
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    private boolean fail = false;

    @GetMapping("/fail-on")
    public String failOn() {
        logger.info("fail mode ON");
        fail = true;
        return "On";
    }

    @GetMapping("/fail-off")
    public String failOff() {
        logger.info("fail mode OFF");
        fail = false;
        return "Off";
    }

    @GetMapping("/fail")
    public Boolean failStatus() {
        return fail;
    }

    @GetMapping(value = "/hi/{name}")
    Map<String, String> hi(@PathVariable String name) {
        if (fail) throw new RuntimeException("Force Fail!");

        logger.info("hi {}", name);
        return Collections.singletonMap("greeting", "Hello, " + name + "!");
    }
}


