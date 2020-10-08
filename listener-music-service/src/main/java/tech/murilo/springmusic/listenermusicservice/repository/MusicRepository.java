package tech.murilo.springmusic.listenermusicservice.repository;

import org.springframework.data.repository.CrudRepository;
import tech.murilo.springmusic.listenermusicservice.model.Music;

public interface MusicRepository extends CrudRepository<Music, String> {
}
