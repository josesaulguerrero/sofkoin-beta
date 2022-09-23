package co.com.sofkoin.beta.infrastructure.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CORSConfiguration implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOriginPatterns("https://sofkoin.web.app", "http://localhost:[*]")
                .allowedMethods("GET")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
