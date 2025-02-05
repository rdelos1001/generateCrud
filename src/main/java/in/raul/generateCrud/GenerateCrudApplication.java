package in.raul.generateCrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GenerateCrudApplication {

	public static void main(String[] args) {
		PropertiesHandler.config(args);
		SpringApplication.run(GenerateCrudApplication.class, args);
	}

}
