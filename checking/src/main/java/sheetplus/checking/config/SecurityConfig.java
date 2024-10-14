package sheetplus.checking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import sheetplus.checking.config.filter.JwtAuthFilter;
import sheetplus.checking.config.security.CustomUserDetailsService;
import sheetplus.checking.handler.JwtAccessDeniedHandler;
import sheetplus.checking.handler.JwtAuthenticationEntryPoint;
import sheetplus.checking.util.JwtUtil;

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


    /**
     * 개발 단계에서는 CORS 미적용, 이후 운영/배포 과정 중에 CORS 설정 추가할 예정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        http.sessionManagement(sessionManagement
                -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtAuthFilter(customUserDetailsService,
                jwtUtil, objectMapper), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(configurer -> {
            configurer.accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper));
            configurer.authenticationEntryPoint(new JwtAuthenticationEntryPoint(objectMapper));
        });

        // 권한 규칙 작성
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers("/private/student/**").hasAnyRole("STUDENT", "ADMIN", "SUPER_ADMIN")
                .requestMatchers("private/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
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
