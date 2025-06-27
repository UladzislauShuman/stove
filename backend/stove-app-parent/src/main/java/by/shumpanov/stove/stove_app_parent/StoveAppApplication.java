package by.shumpanov.stove.stove_app_parent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StoveAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoveAppApplication.class, args);
	}

}
