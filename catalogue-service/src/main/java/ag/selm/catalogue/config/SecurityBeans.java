package ag.selm.catalogue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .securityMatcher("catalogue-api/products/**")   // securityMatcher should be used to define the paths you'd like to be handled by HttpSecurity
                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/**").permitAll();
                    auth.requestMatchers("/**").hasRole("SERVICE").anyRequest().authenticated(); // пользователь и роли указаны в application.yml
                })
                .csrf(csrf -> csrf.disable())   // отменимзащиту от межсайтовой подделки запроса
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
