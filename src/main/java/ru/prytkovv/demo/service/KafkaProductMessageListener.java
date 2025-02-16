package ru.prytkovv.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.prytkovv.demo.proto.Product;


@Slf4j
@Service
public class KafkaProductMessageListener {

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}")
    public void listen(Product product) {
        log.info(String.format("Acquired new product: %s", product.toString()));
    }

}
