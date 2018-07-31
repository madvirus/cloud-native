package edabatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class EtlFlowConfiguration {
    @Bean
    IntegrationFlow etlFlow(
            @Value("${input-directory:${HOME}/temp/in}") File dir,
            BatchChannels c) {
        return IntegrationFlows
                .from(Files.inboundAdapter(dir).autoCreateDirectory(true),
                        cs -> cs.poller(p -> p.fixedRate(1000)))
                .handle(File.class, (file, headers) -> MessageBuilder.withPayload(
                        Tuples.of(file, ThreadLocalRandom.current().nextBoolean())))
                .routeToRecipients(
                        spec -> spec.recipientMessageSelector(c.invalid(), this::isNotFinished)
                        .recipientMessageSelector(c.completed(), this::isFinished)
                )
                .get();
    }

    private boolean isFinished(Message<?> msg) {
        Tuple2<File, Boolean> value = (Tuple2<File, Boolean>) msg.getPayload();
        return value.getT2();
    }

    private boolean isNotFinished(Message<?> msg) {
        return !this.isFinished(msg);
    }
}
