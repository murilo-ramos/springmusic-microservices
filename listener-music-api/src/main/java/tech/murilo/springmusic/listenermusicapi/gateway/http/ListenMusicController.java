package tech.murilo.springmusic.listenermusicapi.gateway.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.murilo.springmusic.listenermusicapi.service.ListenMusicService;
import tech.murilo.springmusic.listenermusicapi.service.S3Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1")
public class ListenMusicController {

    @Autowired
    private ListenMusicService listenMusicService;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/musics/{idMusic}")
    public ResponseEntity<InputStreamResource> create(@PathVariable("idMusic") String idMusic) throws ExecutionException, InterruptedException, IOException {
        var music = listenMusicService.getMusicById(idMusic);

        var s3ObjectInputStream = s3Service.getFileAsStream(music.getPath());
        var resource = new InputStreamResource(s3ObjectInputStream);

        var mediaType = MediaType.parseMediaType("application/octet-stream");
        long len = music.getSize();

        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(len)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + music.getName() + "\"")
                .body(resource);
    }
}
