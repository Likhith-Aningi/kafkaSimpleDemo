package kafka.consumer.app.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import payload.Sample;

@Service
@Slf4j
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        log.info(String.format("Message received -> %s", message));
        messagingTemplate.convertAndSend("/topic/kafkaIn", message);
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeJson(Sample message) {
        log.info(String.format("Json received -> %s", message.toString()));
        messagingTemplate.convertAndSend("/topic/kafkaIn", message);
    }
}
