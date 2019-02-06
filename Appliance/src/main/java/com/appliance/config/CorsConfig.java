package com.appliance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 设置允许跨域
 */
@Configuration
public class CorsConfig {

	/**
	 * 允许任何域名使用 允许任何头 允许任何方法（post、get等）
	 */
	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");// 前端地址"http://localhost:xxxx"
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		// allowCredential 需设置为true
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}
}
