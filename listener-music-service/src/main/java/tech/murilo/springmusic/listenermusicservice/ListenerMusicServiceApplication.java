package tech.murilo.springmusic.listenermusicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableKafka
@EnableAsync
public class ListenerMusicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListenerMusicServiceApplication.class, args);
	}

}
