package tech.murilo.springmusic.listenermusicapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicdata.music.Music;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class ListenMusicService {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    public Music getMusicById(String id) throws ExecutionException, InterruptedException, IOException {
        var mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Music.builder().id(id).build());

        var record = new ProducerRecord<String, String>(requestTopic, json);

        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        var sendAndReceive = kafkaTemplate.sendAndReceive(record);

        var sendResult = sendAndReceive.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        var consumerRecord = sendAndReceive.get();

        var musicReturn = mapper.readValue(consumerRecord.value(), Music.class);

        return musicReturn;
    }
}
