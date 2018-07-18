package demo;

import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Component
public class RibbonCLR implements CommandLineRunner {
    private DiscoveryClient discoveryClient;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RibbonCLR(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void run(String... args) throws Exception {
        String serviceId = "greetings-service";

        List<Server> servers = discoveryClient.getInstances(serviceId).stream()
                .map(si -> new Server(si.getHost(), si.getPort()))
                .collect(toList());

        IRule roundRobinRule = new RoundRobinRule();

        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder()
                .withRule(roundRobinRule)
                .buildFixedServerListLoadBalancer(servers);

        IntStream.range(0, 10).forEach(i -> {
            Server server = loadBalancer.chooseServer();
            URI uri = URI.create("http://" + server.getHost() + ":" + server.getPort() + "/");
            logger.info("resolved service {}", uri);
        });
    }
}
