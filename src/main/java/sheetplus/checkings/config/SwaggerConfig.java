package sheetplus.checkings.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        servers = {@Server(url = "${swagger.prod-url}", description = "운영 서버"),
                   @Server(url = "${swagger.dev-url}", description = "개발 서버")
        })
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().addSecurityItem(new SecurityRequirement()
                        .addList("JWT AccessToken")
                        .addList("JWT RefreshToken"))
                .components(new Components().addSecuritySchemes("JWT AccessToken", createAccessTokenScheme())
                        .addSecuritySchemes("JWT RefreshToken", createRefreshTokenScheme()))
                .info(new Info().title("SheetPlus Application Server API")
                        .description("SheetPlus Application Server API를 명세한 문서입니다.")
                        .version("1.0 ver"));
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
                .name("refreshToken")
                .description("JWT Refresh Token");
    }
}
