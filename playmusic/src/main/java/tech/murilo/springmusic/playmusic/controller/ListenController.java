package tech.murilo.springmusic.playmusic.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController

@RequestMapping("v1")
public class ListenController {

    @RequestMapping("/music")
    @ResponseBody
    public ResponseEntity<InputStreamResource> listenMusic() throws Exception {
        var videoFile = ResourceUtils.getFile("classpath:mp3/01-understairs.mp3");
        var fileLength = Files.size(Paths.get(videoFile.toURI()));
        var mediaType = MediaType.parseMediaType("application/octet-stream");
        var resource = new InputStreamResource(new FileInputStream(videoFile));

        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(fileLength)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ videoFile.getName() + "\"")
                .body(resource);
    }

}
