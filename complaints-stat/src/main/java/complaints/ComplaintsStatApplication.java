package complaints;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComplaintsStatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplaintsStatApplication.class, args);
    }

    @Bean
    SpringAMQPMessageSource statistics(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @Override
            @RabbitListener(queues = "complaints")
            public void onMessage(Message message, Channel channel) {
                super.onMessage(message, channel);
            }
        };
    }
}
