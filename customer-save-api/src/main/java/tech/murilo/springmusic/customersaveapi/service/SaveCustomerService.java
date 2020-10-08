package tech.murilo.springmusic.customersaveapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.customersaveapi.model.Customer;

import java.util.concurrent.ExecutionException;

@Service
public class SaveCustomerService {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    public String save(Customer customer) throws ExecutionException, InterruptedException, JsonProcessingException {
        var mapper = new ObjectMapper();
        var jsonString = mapper.writeValueAsString(customer);

        // montando o producer que ira ser enviado para o kafka
        var producerRecord = new ProducerRecord<String, String>(requestTopic, jsonString);
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        // enviando
        var sendAndReceive = kafkaTemplate.sendAndReceive(producerRecord);

        // pegando retorno
        var sendResult = sendAndReceive.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        var consumerRecord = sendAndReceive.get();

        var createdCustomer = mapper.readValue(consumerRecord.value(), Customer.class);

        return createdCustomer.getId();
    }
}
