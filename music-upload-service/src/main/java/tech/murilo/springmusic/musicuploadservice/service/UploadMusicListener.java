package tech.murilo.springmusic.musicuploadservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicuploadservice.model.MusicStatus;

import java.io.IOException;

@Service
public class UploadMusicListener {
    public static final String UTF_8 = "UTF-8";

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.header.uuid-customer")
    private String headerCustomer;

    @Value("${kafka.header.uuid-music")
    private String headerMusic;

    @Autowired
    private UploadS3Service uploadS3Service;

    @Autowired
    private UpdateMusicStatusService updateMusicStatusService;

    @KafkaListener(topics = "${kafka.topic.request-topic}", groupId = "${kafka.consumer.group-id}")
    public void listen(@Payload ConsumerRecord record,
                       @Headers MessageHeaders messageHeaders) throws IOException {
        var idCustomerBytes = (byte[]) messageHeaders.get(headerCustomer);
        var idMusicBytes = (byte[]) messageHeaders.get(headerMusic);

        String idCustomer = new String(idCustomerBytes, UTF_8);
        String idMusic = new String(idMusicBytes, UTF_8);

        var aByte = (Bytes) record.value();

        var music = uploadS3Service.execute(idCustomer, idMusic, aByte);
        music.setStatus(MusicStatus.READY.toString());

        updateMusicStatusService.execute(music);
    }
}
