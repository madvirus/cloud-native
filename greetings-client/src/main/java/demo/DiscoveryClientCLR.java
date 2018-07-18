package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryClientCLR implements CommandLineRunner {

    private DiscoveryClient discoveryClient;
    private Registration registration;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DiscoveryClientCLR(DiscoveryClient discoveryClient, Registration registration) {
        this.discoveryClient = discoveryClient;
        this.registration = registration;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("localInstance");
        logServiceInstance(registration);

        String serviceId = "greetings-service";
        logger.info("registered instances of {}", serviceId);
        discoveryClient.getInstances(serviceId).forEach(this::logServiceInstance);
    }

    private void logServiceInstance(ServiceInstance si) {
        logger.info("host = {}, port = {}, serviceId = {}",
                si.getHost(), si.getPort(), si.getServiceId());
    }
}
