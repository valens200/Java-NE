package rca.templates.valens.v1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**").allowedOrigins("*");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry handlerRegistry){
        handlerRegistry.addResourceHandler("/**").addResourceLocations("classpath:/static/").addResourceLocations("classpath:/static");
    }
}
