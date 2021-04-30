package com.rlc.rlcbase.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

//@Slf4j
@Service
public class ProducerService {
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//    public ProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
protected Logger logger =  LogManager.getLogger(ProducerService.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;
    public void sendMsg(String topic, String key, String data) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, data);
        future.addCallback(result -> logger.info("生产者成功发送消息到topic:{} key:{} partition:{}的消息", result.getRecordMetadata().topic(),result.getRecordMetadata().offset(), result.getRecordMetadata().partition()),
                ex -> logger.error("生产者发送消失败，原因：{}", ex.getMessage()));
    }
    public void sendMessage(String topic, Object o) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);
        future.addCallback(result -> logger.info("生产者成功发送消息到topic:{} partition:{}的消息", result.getRecordMetadata().topic(), result.getRecordMetadata().partition()),
                ex -> logger.error("生产者发送消失败，原因：{}", ex.getMessage()));
    }
}
