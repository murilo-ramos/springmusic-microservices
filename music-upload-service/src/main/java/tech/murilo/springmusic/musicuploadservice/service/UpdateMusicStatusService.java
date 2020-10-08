package tech.murilo.springmusic.musicuploadservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicuploadservice.model.Music;

@Service
public class UpdateMusicStatusService {

    @Autowired
    @Qualifier("templateUpdate")
    private KafkaTemplate template;

    @Value("${kafka.topic.music-status}")
    private String topicMusicStatus;

    public void execute(Music music) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var musicJson = mapper.writeValueAsString(music);

        var producerRecord = new ProducerRecord<String, String>(topicMusicStatus, musicJson, musicJson);

        template.send(producerRecord);
    }
}
