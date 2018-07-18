package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RoutesListener {
    private RouteLocator routeLocator;
    private DiscoveryClient discoveryClient;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RoutesListener(RouteLocator routeLocator, DiscoveryClient discoveryClient) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
    }

    @EventListener(HeartbeatEvent.class)
    public void onHeartbeanEvent(HeartbeatEvent event) {
        logger.info("onHeartbeanEvent");
        discoveryClient.getServices().stream().forEach(service -> logger.info("service: {}", service) );
    }

    @EventListener(RoutesRefreshedEvent.class)
    public void onRoutesRefreshedEvent(RoutesRefreshedEvent event) {
        logger.info("onRoutesRefreshedEvent");
        this.routeLocator.getRoutes().stream()
                .map((Route x) -> toString())
                .forEach(logger::info);
    }
}
