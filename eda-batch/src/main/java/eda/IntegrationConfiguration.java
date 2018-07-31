package eda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfiguration {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    IntegrationFlow etlFlow(@Value("${input-directory:${HOME}/temp/in}") File dir) {
        return IntegrationFlows
                .from(
                        Files.inboundAdapter(dir).autoCreateDirectory(true),
                        consumer -> consumer.poller(spec -> spec.fixedRate(1000)))
                .handle(File.class, (file, headers) -> {
                    logger.info("We noticed a new file, {}", file);
                    return file;
                })
                .routeToRecipients(spec ->
                        spec
                                .recipientMessageSelector(csv(), (Message<?> msg) -> hasExt(msg.getPayload(), ".csv"))
                                .recipientMessageSelector(txt(), (Message<?> msg) -> hasExt(msg.getPayload(), ".txt"))
                )
                .get();
    }

    private boolean hasExt(Object f, String ext) {
        File file = File.class.cast(f);
        return file.getName().toLowerCase().endsWith(ext.toLowerCase());
    }

    @Bean
    MessageChannel txt() {
        return MessageChannels.direct().get();
    }

    @Bean
    MessageChannel csv() {
        return MessageChannels.direct().get();
    }

    @Bean
    IntegrationFlow txtFlow() {
        return IntegrationFlows.from(txt())
                .handle(File.class, (f, h) -> {
                    logger.info("file is .txt!");
                    return null;
                })
                .get();
    }

    @Bean
    IntegrationFlow csvFlow() {
        return IntegrationFlows.from(csv())
                .handle(File.class, (f, h) -> {
                    logger.info("file is .txt!");
                    return null;
                })
                .get();
    }
}
