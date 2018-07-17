package demo;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class CustomerControllerAdvice {
    private final MediaType vndErrorMediaType =
            MediaType.parseMediaType("application/vnd.error+json");

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<VndErrors> notFound(CustomerNotFoundException e) {
        return this.error(e, HttpStatus.NOT_FOUND, e.getId() + "");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<VndErrors> assertException(IllegalArgumentException e) {
        return this.error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    private <E extends Exception> ResponseEntity<VndErrors> error(
            E error, HttpStatus status, String logref) {
        String msg = Optional.ofNullable(error.getMessage()).orElse(error.getClass().getSimpleName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(vndErrorMediaType);
        return new ResponseEntity<>(new VndErrors(logref, msg), headers, status);
    }
}
