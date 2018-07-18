package demo;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
public class LoadBalancedRestTemplateCLR implements CommandLineRunner {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public LoadBalancedRestTemplateCLR(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> variables = Collections.singletonMap("name", "Cloud Natives!");

        ResponseEntity<JsonNode> response = restTemplate.getForEntity("//greetings-service/hi/{name}", JsonNode.class, variables);
        JsonNode body = response.getBody();
        logger.info("grereting: {}", body.get("greeting").asText());
    }
}
