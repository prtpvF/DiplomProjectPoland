package pl.diplom.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@ComponentScan(basePackages = {"pl.diplom", "pl.diplom.security"})
public class AuthApplication {

        public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        }
}
