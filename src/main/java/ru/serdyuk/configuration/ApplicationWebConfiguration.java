package ru.serdyuk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.serdyuk.web.interseptors.ClientControllerInterceptor;
import ru.serdyuk.web.interseptors.LoggingControllerInterceptor;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingControllerInterceptor());
        registry.addInterceptor(clientControllerInterceptor())
                .addPathPatterns("/api/v1/client/**")
                .addPathPatterns("/api/v2/client/**");
    }

    @Bean
    public LoggingControllerInterceptor loggingControllerInterceptor() {
        return new LoggingControllerInterceptor();
    }

    @Bean
    public ClientControllerInterceptor clientControllerInterceptor() {
        return new ClientControllerInterceptor();
    }
}
