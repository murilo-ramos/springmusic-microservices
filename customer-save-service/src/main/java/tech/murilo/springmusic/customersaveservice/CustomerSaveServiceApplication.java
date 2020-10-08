package tech.murilo.springmusic.customersaveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableKafka
public class CustomerSaveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerSaveServiceApplication.class, args);
	}

}
