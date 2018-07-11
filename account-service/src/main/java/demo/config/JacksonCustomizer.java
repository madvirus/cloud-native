package demo.config;

import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JacksonCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        DateTimeFormatter formatter2 =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        CustomOffsetDateTimeSerializer offsetSerializer =
                new CustomOffsetDateTimeSerializer(formatter2);
        builder
                .simpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializerByType(OffsetDateTime.class, offsetSerializer);
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");
    }

    public class CustomOffsetDateTimeSerializer extends OffsetDateTimeSerializer {
        public CustomOffsetDateTimeSerializer(DateTimeFormatter formatter) {
            super(OffsetDateTimeSerializer.INSTANCE, false, formatter);
        }

    }
}
