package com.atguigu.zxm.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
//配置Swagger的
@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        // 1. 定义 JWT 安全方案
        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("token");

        // 2. 注册安全组件（现在用的是正确的 Component 类，可正常实例化）
        Components components = new Components()
                .addSecuritySchemes("token", jwtScheme);

        // 3. 全局启用 Token 校验
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("token");

        // 4. 组装 OpenAPI
        return new OpenAPI()
                .components(components)
                .security(List.of(securityRequirement))
                .info(new Info()
                        .title("朱学敏API接口文档")
                        .version("1.0")
                        .description("朱学敏API接口文档")
                        .contact(new Contact().name("zxm")));
    }
}