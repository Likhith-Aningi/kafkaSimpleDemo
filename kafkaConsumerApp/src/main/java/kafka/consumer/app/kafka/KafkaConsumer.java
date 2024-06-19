package kafka.consumer.app.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import payload.Sample;

@Service
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message){
        log.info(String.format("Message received -> %s", message));
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeJson(Sample message){
        log.info(String.format("Json received -> %s", message.toString()));
    }
}
