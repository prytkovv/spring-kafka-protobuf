package ru.prytkovv.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.prytkovv.demo.proto.Product;


@Slf4j
@Service
public class KafkaProductMessageListener {

    private final ProductService productService;

    public KafkaProductMessageListener(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}")
    public void listen(Product product, Acknowledgment ack) {
        productService.createProduct(product);
        ack.acknowledge();
    }

}
