package tech.murilo.springmusic.musicaddservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicaddservice.model.Music;
import tech.murilo.springmusic.musicaddservice.model.MusicStatus;
import tech.murilo.springmusic.musicaddservice.repository.MusicRepository;

@Service
public class UpdateMusicListener {

    @Autowired
    private MusicManagementService musicManagementService;

    @KafkaListener(topics = "${kafka.topic.music-status}", groupId = "${kafka.consumergroup}")
    public void listen(Object musicUpdate) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var musicConsumer = (ConsumerRecord) musicUpdate;
        var json = (String) musicConsumer.value();

        var updatedMusic = mapper.readValue(json, Music.class);

        var music = musicManagementService.getMusicById(updatedMusic.getId());

        music.setStatus(updatedMusic.getStatus());
        music.setPath(updatedMusic.getPath());
        music.setSize(updatedMusic.getSize());

        musicManagementService.update(music);
    }
}
