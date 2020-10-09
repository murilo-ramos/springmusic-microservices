package tech.murilo.springmusic.musicaddapi.gateway.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.murilo.springmusic.musicaddapi.service.SaveMusicService;
import tech.murilo.springmusic.musicdata.music.Music;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1")
public class MusicController {

    @Autowired
    private SaveMusicService saveMusicService;

    @PostMapping("/customers/{id}/musics")
    public String create(@PathVariable ("id") String idCustomer, @RequestBody Music music) throws ExecutionException, InterruptedException, JsonProcessingException {
        music.setIdCustomer(idCustomer);
        return saveMusicService.save(music);
    }

}
