package com.workfall.jwt_checking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = @Server(
                description = "localhost ENVIRONMENT",
                url = "http://localhost:8080/"
        ),
        info = @Info(

                version = "v1" ,
                title = "JWT APP" ,
                description = "JWT MANAGEMENT" ,
                summary = "learning JWT",
                contact = @Contact(
                        name = "Abhishek",
                        email = "workspace.abhishek.08@gmail.com"
                )
        )
)
public class OpenAPIConfig {

}
