package pl.diplom.common.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "pl.diplom.common.repository")
@ComponentScan(basePackages = "pl.diplom.common")
@EntityScan(basePackages = "pl.diplom.common.model")
@Configuration
public class ConfigurationModule{
}
