package stream.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class StreamConsumerApplication {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerApplication.class, args);
    }

    private IntegrationFlow incomingMessageFlow(SubscribableChannel incoming,
                                                String prefix) {
        return IntegrationFlows
                .from(incoming)
                .transform(String.class, String::toUpperCase)
                .handle(String.class, (greeting, headers) -> {
                    logger.info("greeting received in IntegrationFlow ({}): {}", prefix, greeting);
                    return null;
                })
                .get();
    }

    @Bean
    IntegrationFlow direct(ConsumerChannels channels) {
        return incomingMessageFlow(channels.directed(), "directed");
    }

    @Bean
    IntegrationFlow broadcast(ConsumerChannels channels) {
        return incomingMessageFlow(channels.broadcasts(), "broadcast");
    }
}
