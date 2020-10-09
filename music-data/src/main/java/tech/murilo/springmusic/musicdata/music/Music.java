package tech.murilo.springmusic.musicdata.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    private String id;

    @NotNull
    @NotEmpty
    private String idCustomer;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String status;

    private String path;

    private long size;
}

