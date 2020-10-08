package tech.murilo.springmusic.musicuploadapi.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicuploadapi.model.MusicUpload;

import java.io.IOException;

@Service
public class UploadMusicService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    String requestTopic;

    @Value("${kafka.header.uuid-customer")
    String headerCustomer;

    @Value("${kafka.header.uuid-music")
    String headerMusic;

    public void execute(MusicUpload musicUpload) throws IOException {

        var musicBytes = new Bytes(musicUpload.getFile().getBytes());

        var producerRecord = new ProducerRecord<Bytes, Bytes>(requestTopic, musicBytes, musicBytes);
        producerRecord.headers().add(new RecordHeader(headerCustomer, musicUpload.getIdCustomer().getBytes()));
        producerRecord.headers().add(new RecordHeader(headerMusic, musicUpload.getIdMusic().getBytes()));

        kafkaTemplate.send(producerRecord);
    }

}
