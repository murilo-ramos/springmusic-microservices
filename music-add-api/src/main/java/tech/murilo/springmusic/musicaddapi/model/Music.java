package tech.murilo.springmusic.musicaddapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Music {

    private String id;

    @NotNull
    @NotEmpty
    private String idCustomer;

    @NotNull
    @NotEmpty
    private String name;

    private String path;

    private long size;
}
