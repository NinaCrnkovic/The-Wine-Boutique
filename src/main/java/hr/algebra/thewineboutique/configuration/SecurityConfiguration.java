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
                        .requestMatchers("/TheWineBoutique/login", "/TheWineBoutique/login2", "/TheWineBoutique/register", "/TheWineBoutique/home", "/TheWineBoutique/wineStore", "/TheWineBoutique/allwines", "/TheWineBoutique/search", "/TheWineBoutique/ajaxSearch", "/TheWineBoutique/category/**", "/TheWineBoutique/all", "/TheWineBoutique/cart/add", "/TheWineBoutique/cart","/TheWineBoutique/cart/view", "/TheWineBoutique/loginPrompt", "/TheWineBoutique/cart/checkout", "/TheWineBoutique/cart/view").permitAll()
                        .requestMatchers("/TheWineBoutique/orders","/TheWineBoutique/paypal/execute", "/TheWineBoutique/succes","/TheWineBoutique/cancel","/TheWineBoutique/pay","/TheWineBoutique/order/submit", "/TheWineBoutique/order", "/TheWineBoutique/order/complete", "/TheWineBoutique/orderConfirmation", "/TheWineBoutique/paypal").hasRole("USER")
                        .requestMatchers("/TheWineBoutique/paypal/execute","/TheWineBoutique/orders", "/TheWineBoutique/loginLogs",
                                "/TheWineBoutique/succes","/TheWineBoutique/cancel","/TheWineBoutique/pay", "/TheWineBoutique/order/submit","/TheWineBoutique/order", "/TheWineBoutique/orderConfirmation", "/TheWineBoutique/order/complete","/TheWineBoutique/paypal", "/TheWineBoutique/newWine", "/TheWineBoutique/edit/**", "/TheWineBoutique/delete/**", "/TheWineBoutique/confirmDelete/**").hasRole("ADMIN")
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
                )
        .sessionManagement(session -> session
                .sessionFixation().migrateSession() // Ensure session fixation protection
                .maximumSessions(1) // Only one session per user
                .expiredUrl("/TheWineBoutique/login?expired") // Redirect on session expiry
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}
