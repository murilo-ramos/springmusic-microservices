package tech.murilo.springmusic.musicaddservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    @PrimaryKey
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
