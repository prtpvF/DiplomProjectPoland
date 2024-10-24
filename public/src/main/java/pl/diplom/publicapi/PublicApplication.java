package pl.diplom.publicapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pl.diplom")
public class PublicApplication {

		public static void main(String[] args) {
			SpringApplication.run(PublicApplication.class, args);

		}

		@Bean
		public ModelMapper modelMapper() {
			return new ModelMapper();
		}
}
