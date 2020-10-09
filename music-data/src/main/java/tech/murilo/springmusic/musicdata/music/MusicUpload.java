package tech.murilo.springmusic.musicdata.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicUpload {
    private String idCustomer;
    private String idMusic;
    private MultipartFile file;
}
