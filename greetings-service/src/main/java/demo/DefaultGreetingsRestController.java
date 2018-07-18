package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Profile({ "default", "insecure"} )
@RestController
@RequestMapping(method = RequestMethod.GET, value = "/greet/{name}")
public class DefaultGreetingsRestController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping
    Map<String, String> hi(@PathVariable String name) {
        logger.info("hi {}", name);
        return Collections.singletonMap("greeting", "Hello, " + name + "!");
    }
}
