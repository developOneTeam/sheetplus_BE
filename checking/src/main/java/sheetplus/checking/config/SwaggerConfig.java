package sheetplus.checking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().addSecurityItem(new SecurityRequirement()
                        .addList("JWT AccessToken")
                        .addList("JWT RefreshToken"))
                .components(new Components().addSecuritySchemes("JWT AccessToken", createAccessTokenScheme())
                        .addSecuritySchemes("JWT RefreshToken", createRefreshTokenScheme()))
                .info(new Info().title("Chekcing App API")
                        .description("This is How to Use API")
                        .version("v0.1"));
    }


    private SecurityScheme createAccessTokenScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("JWT Access Token");
    }

    private SecurityScheme createRefreshTokenScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("refresh-token")
                .description("JWT Refresh Token");
    }
}
