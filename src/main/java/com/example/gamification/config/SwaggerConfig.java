
package com.example.gamification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        String jwtSchemeName = "bearerAuth";

        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList(jwtSchemeName);

        SecurityScheme securityScheme =
                new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("Gamification API")
                        .description("Gamification 프로젝트 API 문서")
                        .version("1.0.0"))

                // 현재 접속한 서버 기준으로 API 호출
                .addServersItem(new Server()
                        .url("/")
                        .description("Current Server"))

                // JWT 인증 설정
                .addSecurityItem(securityRequirement)
                .schemaRequirement(jwtSchemeName, securityScheme);
    }
}
