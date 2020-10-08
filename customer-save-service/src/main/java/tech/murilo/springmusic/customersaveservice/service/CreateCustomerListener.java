package tech.murilo.springmusic.customersaveservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.customersaveservice.model.Customer;

@Service
public class CreateCustomerListener {

    @Autowired
    private CreateCustomerService createCustomerService;

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public String listen(String json) throws InterruptedException, JsonProcessingException {
        var mapper = new ObjectMapper();
        var customer = mapper.readValue(json, Customer.class);

        createCustomerService.save(customer);

        return mapper.writeValueAsString(customer);
    }
}
