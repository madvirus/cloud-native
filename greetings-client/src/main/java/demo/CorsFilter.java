package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class CorsFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, List<ServiceInstance>> catalog = new ConcurrentHashMap<>();
    private DiscoveryClient discoveryClient;

    @Autowired
    public CorsFilter(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.refreshCatalog();
    }

    private void refreshCatalog() {
        discoveryClient.getServices().forEach(svc -> this.catalog.put(svc, discoveryClient.getInstances(svc)));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = HttpServletRequest.class.cast(req);
        HttpServletResponse response = HttpServletResponse.class.cast(resp);
        String originHeaderValue = originFor(request);
        boolean clientAllowed = isClientAllowed(originHeaderValue);
        logger.info("client {} allowed: {}", originHeaderValue, clientAllowed);

        if (clientAllowed) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeaderValue);
        }
        filterChain.doFilter(req, resp);
    }

    private String originFor(HttpServletRequest request) {
        return StringUtils.hasText(request.getHeader(HttpHeaders.ORIGIN))
                ? request.getHeader(HttpHeaders.ORIGIN)
                : request.getHeader(HttpHeaders.REFERER);
    }

    private boolean isClientAllowed(String origin) {

        if (StringUtils.hasText(origin)) {
            URI originUri = URI.create(origin);
            int port = originUri.getPort();
            String match = originUri.getHost() + ":" + (port <= 0 ? 80 : port);
            boolean svcMatch = catalog.keySet()
                    .stream()
                    .anyMatch(serviceId ->
                            catalog.get(serviceId).stream()
                                .map(si -> si.getHost() + ":" + si.getPort())
                            .anyMatch(hp -> hp.equalsIgnoreCase(match))
                    );
            return svcMatch;
        }
        return false;
    }

    @EventListener(HeartbeatEvent.class)
    public void onHeartbeatEvent(HeartbeatEvent event) {
        this.refreshCatalog();
    }

    @Override
    public void destroy() {
    }
}
