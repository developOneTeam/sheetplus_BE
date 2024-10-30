package sheetplus.checkings.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import sheetplus.checkings.config.filter.JwtAuthFilter;
import sheetplus.checkings.config.filter.JwtExceptionFilter;
import sheetplus.checkings.config.security.CustomUserDetailsService;
import sheetplus.checkings.util.JwtUtil;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/member/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/public/**"
    };

    @Value("${security.cors-urls.list}")
    private List<String> corUrls;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(custom -> custom.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.setAllowedOrigins(corUrls);
            config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PATCH", "OPTIONS"));
            config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control", "Set-Cookie"));
            config.setExposedHeaders(List.of("Set-Cookie", "Authorization"));
            return config;
        }));

        http.sessionManagement(sessionManagement
                -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtAuthFilter(customUserDetailsService, jwtUtil)
                , UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtExceptionFilter(objectMapper), JwtAuthFilter.class);

        // 권한 규칙 작성
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers("/private/student/**").hasAnyRole("STUDENT", "ADMIN", "SUPER_ADMIN")
                .requestMatchers("/private/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .requestMatchers("/private/super/admin/**").hasRole("SUPER_ADMIN")
                .requestMatchers("/private/**").authenticated()
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
