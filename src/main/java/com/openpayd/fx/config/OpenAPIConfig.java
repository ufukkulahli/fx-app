package com.openpayd.fx.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {

        var local = new Server();
        local.setUrl("http://localhost:8080");
        local.setDescription("FX App");

        var server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Server URL of FX App");


        Contact contact = new Contact();
        contact.setEmail("test@test.com");
        contact.setName("test test");
        contact.setUrl("https://www.test.com");

        License mitLicense = new License().name("Licence type").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("FX App API")
                .version("1.0")
                .contact(contact)
                .description("Open API code generation and swagger ui of FX App")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(server, local));
    }
}