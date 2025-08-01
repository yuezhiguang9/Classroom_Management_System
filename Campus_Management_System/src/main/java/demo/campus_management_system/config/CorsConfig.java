package demo.campus_management_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") //匹配所有的源/
                .allowedOrigins("http://localhost:5173") // 允许的前端源
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
                .allowedHeaders("*")           // 允许所有请求头
                .allowCredentials(true)        // 允许发送 Cookie
                .maxAge(3600);                 // 预检请求缓存时间（秒）
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}