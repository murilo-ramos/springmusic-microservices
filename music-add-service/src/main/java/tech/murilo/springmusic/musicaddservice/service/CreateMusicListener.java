package tech.murilo.springmusic.musicaddservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicaddservice.model.Music;
import tech.murilo.springmusic.musicaddservice.model.MusicStatus;

@Service
public class CreateMusicListener {

    @Autowired
    private MusicManagementService musicManagementService;

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public String listen(String json) throws InterruptedException, JsonProcessingException {
        var mapper = new ObjectMapper();

        Music music = mapper.readValue(json, Music.class);
        music.setStatus(MusicStatus.WAIT_SAVE_PATH.name());

        musicManagementService.add(music);

        return mapper.writeValueAsString(music);
    }
}
