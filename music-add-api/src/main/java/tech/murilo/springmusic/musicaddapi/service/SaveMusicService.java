package tech.murilo.springmusic.musicaddapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicaddapi.model.Music;

import java.util.concurrent.ExecutionException;

@Service
public class SaveMusicService {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    public String save(Music music) throws ExecutionException, InterruptedException, JsonProcessingException {
        var mapper = new ObjectMapper();
        var jsonString = mapper.writeValueAsString(music);

        var record = new ProducerRecord<String, String>(requestTopic, jsonString);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        var sendAndReceive = kafkaTemplate.sendAndReceive(record);

        var sendResult = sendAndReceive.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key()  + ":" + header.value().toString()));
        var consumerRecord  = sendAndReceive.get();

        var musicResponse = mapper.readValue(consumerRecord.value(), Music.class);

        return musicResponse.getId();
    }

}
