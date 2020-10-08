package tech.murilo.springmusic.musicuploadservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    private String id;

    private String idCustomer;

    private String name;

    private String path;

    private long size;

    private String status;
}
