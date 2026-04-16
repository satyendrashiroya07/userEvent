package shiroya.userEvent.userEvent.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import shiroya.userEvent.UserEvent;

@Service
@RequiredArgsConstructor
public class userProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void sendOrderEvent(UserEvent event)
    {
        kafkaTemplate.send("user-created", event.getUserName(), event);
    }

}
