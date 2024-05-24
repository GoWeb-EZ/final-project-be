package group10.doodling.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용할 오리진 설정 (현재는 로컬에서만 테스트)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드 설정
                .allowedHeaders("*") // 허용할 헤더 설정, 별표(*)는 모든 헤더를 허용
                .allowCredentials(true); // 모든 오리진을 허용하면 동작 오류, 특정 오리진을 설정해주어야 함.
    }
}
