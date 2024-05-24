package group10.doodling.config;

import group10.doodling.component.TokenManager;
import group10.doodling.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용할 오리진 설정 (현재는 로컬에서만 테스트)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드 설정
                .allowedHeaders("*") // 허용할 헤더 설정, 별표(*)는 모든 헤더를 허용
                .allowCredentials(true); // 모든 오리진을 허용하면 동작 오류, 특정 오리진을 설정해주어야 함.
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> customFilterRegistration() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter(tokenManager));
        registrationBean.addUrlPatterns("/*"); // Filter가 적용될 URL 패턴 설정
        return registrationBean;
    }
}
