package com.example.user_service.Interface.Impl;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class kafkaservice {
    private final StreamBridge streamBridge;

    public kafkaservice(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }
    public void send(productDTO prod) {
//        System.out.println("Retry");
//        if (true) {
//            throw new RuntimeException("Simulated Kafka send failure");
//        }
        streamBridge.send("user-out-0", prod);
    }
}
