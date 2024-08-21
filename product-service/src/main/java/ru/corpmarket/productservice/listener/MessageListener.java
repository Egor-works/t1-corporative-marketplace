package ru.corpmarket.productservice.listener;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.corpmarket.productservice.dto.KafkaOrderDto;
import ru.corpmarket.productservice.exception.RunOutProductException;
import ru.corpmarket.productservice.service.ProductService;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final ProductService productService;

    @KafkaListener(topics = "${kafka.topic.consumer.order-clothes-topic}",
            groupId = "${kafka.consumer-group.order-clothes}", containerFactory = "orderListener")
    void kafkaOrderListener(ConsumerRecord<String, KafkaOrderDto> consumerRecord) {
        try {
            productService.updateProductCount(consumerRecord.value());
        } catch (NotFoundException | RunOutProductException e) {
            e.printStackTrace();
        }
    }
}
