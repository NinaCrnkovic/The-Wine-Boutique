package hr.algebra.thewineboutique.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow access to static resources
                        .requestMatchers("/TheWineBoutique/login", "/TheWineBoutique/register", "/TheWineBoutique/home", "/TheWineBoutique/wineStore", "/TheWineBoutique/allwines", "/TheWineBoutique/search", "/TheWineBoutique/ajaxSearch", "/TheWineBoutique/category/**", "/TheWineBoutique/all").permitAll()
                        .requestMatchers("/TheWineBoutique/newWine", "/TheWineBoutique/edit/**", "/TheWineBoutique/delete/**", "/TheWineBoutique/confirmDelete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/TheWineBoutique/login")
                        .defaultSuccessUrl("/TheWineBoutique/wineStore", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/TheWineBoutique/logout")
                        .logoutSuccessUrl("/TheWineBoutique/home")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}
