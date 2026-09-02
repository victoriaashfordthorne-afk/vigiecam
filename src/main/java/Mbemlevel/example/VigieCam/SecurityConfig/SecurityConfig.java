package Mbemlevel.example.VigieCam.SecurityConfig;



import Mbemlevel.example.VigieCam.OAuth2UserService.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(
            CustomOAuth2UserService customOAuth2UserService
    ) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // OAuth2 login
                        .requestMatchers(
                                "/",
                                "/oauth2/**",
                                "/login/**"
                        ).permitAll()

                        // Moderator endpoints
                        .requestMatchers(
                                "/api/moderator/**"
                        ).hasRole("MODERATOR")

                        // Everything else requires login
                        .anyRequest()
                        .authenticated()
                )

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(
                                        customOAuth2UserService
                                )
                        )
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }
}
