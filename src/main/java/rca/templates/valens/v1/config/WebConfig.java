package rca.templates.valens.v1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Configure CORS mappings
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**").allowedOrigins("*");
    }

    // Configure resource handlers for serving static resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry handlerRegistry){
        handlerRegistry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/") // Location of static resources in the classpath
                .addResourceLocations("classpath:/static"); // Additional location for static resources
    }
}
