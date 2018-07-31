package stream.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerChannels {
    String DIRECT = "directGreetings";
    String BROADCAST = "broadcastGreetings";

    @Output(DIRECT)
    MessageChannel directGreetings();

    @Output(BROADCAST)
    MessageChannel broadcastGreetings();

}
