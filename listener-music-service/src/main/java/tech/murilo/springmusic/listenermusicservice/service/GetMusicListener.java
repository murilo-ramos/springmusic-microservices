package tech.murilo.springmusic.listenermusicservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.listenermusicservice.model.Music;
import tech.murilo.springmusic.listenermusicservice.repository.MusicRepository;

@Service
public class GetMusicListener {

    @Autowired
    private MusicRepository musicRepository;

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public String listen(String json) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var music = mapper.readValue(json, Music.class);

        var optionalMusic = musicRepository.findById(music.getId());
        var musicReturn = Music
                .builder()
                .path(optionalMusic.get().getPath())
                .size(optionalMusic.get().getSize())
                .build();

        return mapper.writeValueAsString(musicReturn);
    }
}
