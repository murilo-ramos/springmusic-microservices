package tech.murilo.springmusic.musicuploadapi.gateway.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.murilo.springmusic.musicuploadapi.model.MusicUpload;
import tech.murilo.springmusic.musicuploadapi.service.UploadMusicService;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class MusicUploadController {

    @Autowired
    private UploadMusicService uploadMusicService;

    @PostMapping("/customers/{idCustomer}/musics/{idMusic}")
    public ResponseEntity<?> create(@PathVariable("idCustomer") String idCustomer,
                                    @PathVariable("idMusic") String idMusic,
                                    @RequestParam("file") MultipartFile file) throws IOException {
        uploadMusicService.execute(MusicUpload
            .builder()
            .idCustomer(idCustomer)
            .idMusic(idMusic)
            .file(file)
            .build()
        );

        return ResponseEntity.ok().build();
    }
}
