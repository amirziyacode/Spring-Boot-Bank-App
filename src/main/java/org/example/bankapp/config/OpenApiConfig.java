package org.example.bankapp.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "AmirZiya",
                        email = "amzdidi@gmail.com",
                        url = "https://github.com/amirziyacode"
                ),
                title = "Open API",
                description = "Open API Bank Application",
                version = "1.2"
        )
)
@SecurityScheme(
        name = "UserSecurity",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        in = SecuritySchemeIn.HEADER,
        description = "Basic Authentication using username and password"
)
@Configuration
public class OpenApiConfig { }
