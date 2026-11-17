package com.example.hoteltourist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Hotel Tourist Management API",
        version = "1.0",
        description = "API for updating tourist contact and destination details"
))
@SecurityScheme(
        name = "RequestToken",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-Request-Token"
)
public class OpenApiConfig {
}
