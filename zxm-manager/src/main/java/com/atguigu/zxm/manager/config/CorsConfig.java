package com.atguigu.zxm.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 关键1：保留 allowCredentials=true（因为前端需要传 Token）
        config.setAllowCredentials(true);

        // 关键2：用 allowedOriginPatterns 替换 allowedOrigins，解决冲突
        config.addAllowedOriginPattern("*"); // 支持通配符，且兼容凭证传递
        // （如果想更严格，也可以写具体前端域名：config.addAllowedOriginPattern("http://5247b578.r34.cpolar.top")）

        config.addAllowedMethod("*"); // 允许所有请求方法（GET/POST/PUT/DELETE）
        config.addAllowedHeader("*"); // 允许所有请求头（Token、Content-Type 等）
        config.setMaxAge(3600L); // 跨域缓存1小时，减少重复校验

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 所有接口都生效

        return new CorsFilter(source);
    }
}
