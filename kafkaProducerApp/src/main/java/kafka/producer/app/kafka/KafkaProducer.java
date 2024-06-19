package kafka.producer.app.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import payload.Sample;

@Service
@Slf4j
public class KafkaProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Value("${spring.kafka.topic-json.name}")
    private String jsonTopicName;

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String,Sample> kafkaJsonTemplate;
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate,KafkaTemplate<String, Sample> kafkaJsonTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaJsonTemplate=kafkaJsonTemplate;
    }

    public void sendMessage(String message) {
        log.info(String.format("Message sent to %s --> %s", topicName, message));
        var future = kafkaTemplate.send(topicName, message);
        try {
            log.info(future.get().toString());
        } catch (Exception e) {
            log.error("msg sent failure");
        }
    }

    public void sendJson(Sample message) {
        var future = kafkaJsonTemplate.send(jsonTopicName, message);
        try {
            log.info(future.get().toString());
        } catch (Exception e) {
            log.error("json msg sent failure");
        }
    }

}
