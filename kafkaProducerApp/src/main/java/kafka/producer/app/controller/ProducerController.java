package kafka.producer.app.controller;

import kafka.producer.app.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.Sample;

@RestController
public class ProducerController {
    @Autowired
    KafkaProducer kafkaProducer;
    @GetMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("msg") String msg){
        kafkaProducer.sendMessage(msg);
        return ResponseEntity.ok("message sent !!");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishSampleJson(@RequestBody Sample msg){
        kafkaProducer.sendJson(msg);
        return ResponseEntity.ok("json sent !!");
    }
}
