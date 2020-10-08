package tech.murilo.springmusic.musicaddservice.repository;

import org.springframework.data.repository.CrudRepository;
import tech.murilo.springmusic.musicaddservice.model.Music;

public interface MusicRepository extends CrudRepository<Music, String> {
}
