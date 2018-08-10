package complaints;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping(value = "/complaints", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintsRestController {
    private final CommandGateway cg;

    @Autowired
    public ComplaintsRestController(CommandGateway cg) {
        this.cg = cg;
    }

    @PostMapping
    CompletableFuture<ResponseEntity<?>> createComplaint(@RequestBody Map<String, String> body) {
        log.info("receive createComplaint request");
        String id = UUID.randomUUID().toString();
        FileComplaintCommand complaint = new FileComplaintCommand(id, body.get("company"), body.get("description"));

        return this.cg.send(complaint).thenApply(
                complaintId -> {
                    log.info("handle: command={}, complaintId={}", complaint, complaintId);
                    URI uri = uri("/complaints/{id}", Collections.singletonMap("id", complaint.getId()));
                    return ResponseEntity.created(uri).build();
                });
    }

    @PostMapping("/{complaintId}/comments")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CompletableFuture<ResponseEntity<?>> addComment(@PathVariable String complaintId,
                                                    @RequestBody Map<String, Object> body) {
        log.info("receive addComment request");
        Long when = Long.class.cast(body.getOrDefault("when", System.currentTimeMillis()));
        AddCommentCommand command = new AddCommentCommand(complaintId,
                UUID.randomUUID().toString(),
                String.class.cast(body.get("comment")),
                String.class.cast(body.get("user")),
                new Date(when));

        return cg.send(command).thenApply(commentId -> {
            log.info("handle: command={}, complaintId={}", command, complaintId);
            Map<String, String> params = new HashMap<>();
            params.put("complaintId", complaintId);
            params.put("commentId", command.getCommentId());

            URI uri = uri("/complaints/{complaintId}/comments/{commentId}", params);
            return ResponseEntity.created(uri).build();
        });
    }

    @DeleteMapping("/{complaintId}")
    CompletableFuture<ResponseEntity<?>> closeComplaint(@PathVariable String complaintId) {
        log.info("receive closeComplaint request");
        CloseComplaintCommand csc = new CloseComplaintCommand(complaintId);

        return cg.send(csc).thenApply(none -> {
                log.info("handle: command={}, complaintId={}", csc, none);
                return ResponseEntity.notFound().build();
        });
    }


    private URI uri(String uri, Map<String, String> template) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().path(uri).build().expand(template);
        return uriComponents.toUri();
    }
}
