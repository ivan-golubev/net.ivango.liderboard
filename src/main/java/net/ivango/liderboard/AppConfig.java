package net.ivango.liderboard;

import net.ivango.liderboard.rest.LiderboardResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("net.ivango.liderboard")
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(LiderboardResource.class);
    }
}