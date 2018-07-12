package demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class UserService {
    private final RestTemplate restTemplate;
    private final String serviceHost;

    @Autowired
    public UserService(RestTemplate restTemplate,
            @Value("${user-service.host:user-service}") String serviceHost) {
        this.restTemplate = restTemplate;
        this.serviceHost = serviceHost;
    }

    public User getAuthenticatedUser() {
        URI uri = URI.create(String.format("http://%s:8888/uaa/v1/me", serviceHost));
        RequestEntity<Void> request = RequestEntity.get(uri).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        return restTemplate.exchange(request, User.class).getBody();
    }
}
