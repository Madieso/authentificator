package com.lma.authentificator.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppCorsConfiguration {

	/** The front end url. */
	@Value("${frontend.url}")
	private String crossOrigin;

	//	@Bean
	//	public FilterRegistrationBean corsFilter() {
	//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	//		final CorsConfiguration config = new CorsConfiguration();
	//		config.setAllowCredentials(true);
	//		config.addAllowedOrigin(this.crossOrigin);
	//		config.addAllowedHeader("*");
	//		config.addAllowedMethod("*");
	//		source.registerCorsConfiguration("/**", config);
	//		final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	//		bean.setOrder(0);
	//		return bean;
	//	}


	@Bean
	public FilterRegistrationBean corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

}