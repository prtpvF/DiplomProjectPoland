package pl.diplom.clients.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfig {

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
}
