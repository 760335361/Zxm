package com.atguigu.zxm.manager.config;

import com.atguigu.zxm.manager.interceptor.LoginAuthInterceptor;
import com.atguigu.zxm.manager.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// 注意：用 @Configuration 而非 @Component，WebMvc 配置类推荐用 @Configuration
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;

    @Autowired
    private UserProperties userProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                        // 放行 Knife4j/Springdoc 相关路径
                        "/doc.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/v3/api-docs.yaml",
                        // 放行 IndexController 所有接口（/admin/system/index/** 前缀）
                        "/admin/system/index/**"
                );
    }

    // 优化跨域配置（避免通配符 * 与 allowCredentials=true 冲突）
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true) // 允许跨域传递 Cookie（验证码、Session 依赖）
                // 修正：allowedOriginPatterns 用具体匹配规则，不要直接用 *（部分浏览器不兼容）
                .allowedOriginPatterns("http://localhost:*", "https://*.cpolar.top")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 明确允许的请求方法
                .allowedHeaders("*") // 允许所有请求头（包括 Authorization、token 等）
                .maxAge(3600); // 预检请求缓存时间（1小时，减少跨域请求次数）
    }
}