package ch.project.quizme.validator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is used to enable CORS for the application.
 * This is required for the application to accept all HTTP methods.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-3
 */

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD");
    }
}
