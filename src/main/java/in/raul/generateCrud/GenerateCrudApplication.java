package in.raul.generateCrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GenerateCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateCrudApplication.class, args);
		PropertiesHandler.config(args);
	}

}
