package com.cinema.wasai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    // Spring 为我们提供了一个CorsFiler 我们只需要把他放到容器来即可
    @Bean
    public CorsWebFilter corsWebFilter() {
        // 跨域的配置信息   springframework.web.cors.reactive 包下的
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 支持哪些来源的请求
        corsConfiguration.addAllowedOrigin("*");
        // 支持的请求头
        corsConfiguration.addAllowedHeader("*");
        // 支持哪些请求方式
        corsConfiguration.addAllowedMethod("*");
        // 是否允许携带 cookie
        corsConfiguration.setAllowCredentials(true);

        // 注册跨域的配置信息，任意路径下
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(corsConfigurationSource);
    }
}