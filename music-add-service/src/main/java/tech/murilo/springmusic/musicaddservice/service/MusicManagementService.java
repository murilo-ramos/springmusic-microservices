package tech.murilo.springmusic.musicaddservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicaddservice.model.Music;
import tech.murilo.springmusic.musicaddservice.repository.MusicRepository;

import java.util.UUID;

@Service
public class MusicManagementService {

    @Autowired
    private MusicRepository musicRepository;

    public Music getMusicById(String id) {
        return musicRepository.findById(id).get();
    }

    public void add(Music music) {
        music.setId(UUID.randomUUID().toString());
        musicRepository.save(music);
    }

    public void update(Music music) {
        musicRepository.save(music);
    }
}
