package edabatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import reactor.util.function.Tuple2;

import java.io.File;

@Configuration
public class FileFlowConfiguration {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    IntegrationFlow finishedJobsFlow(BatchChannels channels,
                                     @Value("${input-directory:${HOME}/temp/completed}") File finished) {
        return IntegrationFlows
                .from(channels.completed())
                .handle(Tuple2.class, (tup, headers) -> {
                    File file = (File) tup.getT1();
                    logger.info("finished {}", file);
                    move(file, finished);
                    return null;
                })
                .get();
    }

    @Bean
    IntegrationFlow invalidJobsFlow(BatchChannels channels,
                                     @Value("${input-directory:${HOME}/temp/error}") File errors) {
        return IntegrationFlows
                .from(channels.invalid())
                .handle(Tuple2.class, (tup, headers) -> {
                    File file = (File) tup.getT1();
                    logger.info("invalid {}", file);
                    move(file, errors);
                    return null;
                })
                .get();
    }

    private void move(File file, File dest) {
        if (!dest.exists()) dest.mkdirs();
        File destFile = new File(dest, file.getName());
        file.renameTo(destFile);
    }
}
