package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/customers/{id}/photo")
public class CustomerProfilePhotoRestController {
    private File root = new File(".");
    private final CustomerRepository customerRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public CustomerProfilePhotoRestController(
            @Value("${upload.dir:${user.home}/images") String uploadDir,
            CustomerRepository customerRepository) {
        this.root = new File(uploadDir);
        this.customerRepository = customerRepository;
    }

    @GetMapping
    ResponseEntity<Resource> read(@PathVariable Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    File file = fileFor(customer);
                    Resource fileResource = new FileSystemResource(file);
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(fileResource);
                }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    Callable<ResponseEntity<?>> write(@PathVariable("id") Long id,
                                      @RequestParam("file") MultipartFile file) {
        logger.info("upload-start /customers/{}/photo ({} bytes)", id, file.getSize());

        return () ->
                customerRepository
                        .findById(id)
                        .map(customer -> {
                            File fileForCustomer = fileFor(customer);
                            try (InputStream in = file.getInputStream();
                                 OutputStream out = new FileOutputStream(fileForCustomer)) {
                                FileCopyUtils.copy(in, out);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                    .buildAndExpand(id).toUri();
                            return ResponseEntity.created(location).build();
                        }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private File fileFor(Customer person) {
        return new File(root, Long.toString(person.getId()));
    }

}
