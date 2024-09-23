package pl.diplom.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.diplom.security.logout.LogoutHandlerImpl;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final LogoutHandlerImpl logoutHandler;
  //  private final JwtFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> request.requestMatchers(
                        "/auth/registration",
                        "/auth/login",
                        "/public/party/**",
                        "/public/person/find",
                        "/auth/logout",
                        "/admin/all/drinks",
                        "/css/**",
                        "/js/**").permitAll()
                        .anyRequest()
                        .authenticated());
                http.csrf(AbstractHttpConfigurer::disable);
                http.cors(AbstractHttpConfigurer::disable);
                //.addFilterBefore(filter,
                //        UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling
//                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//                .csrf(AbstractHttpConfigurer::disable);
        http.logout((logout) -> logout.logoutUrl("/auth/logout").addLogoutHandler(logoutHandler));
        return http.build();
    }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}