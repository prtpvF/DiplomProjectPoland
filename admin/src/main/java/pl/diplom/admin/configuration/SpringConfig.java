package pl.diplom.admin.configuration;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {


        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

}