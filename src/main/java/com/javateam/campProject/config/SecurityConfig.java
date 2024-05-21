package com.javateam.campProject.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.javateam.campProject.domain.Role;
import com.javateam.campProject.service.CustomOAuth2UserService;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    
    // security 적용 예외 URL 등록
    // Swagger 페이지 접근에 대한 예외 처리
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//    	
//    	return (web) -> web.ignoring().antMatchers("/", "/css/**", "/webjars/**", 
//    				"/images/**", "/js/**", "/profile");
//    }

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    	http.csrf(csrfCustomizer -> csrfCustomizer.disable());
    	
        http.headers(headersCustomizer -> headersCustomizer
        				.frameOptions(Customizer.withDefaults()).disable());
        
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> 
        							authorizeHttpRequestsCustomizer
        								.requestMatchers("/", "/css/**", "/webjars/**","/img/**","/vendor/**",
    													"/images/**", "/js/**", "/profile").permitAll()
    									.requestMatchers("/api/v1/**").hasRole(Role.USER.name())
										// .requestMatchers("/login/oauth2/**").hasRole(Role.USER.name())
    									.anyRequest().authenticated()); 
                
        http.logout(logoutCustomizer -> logoutCustomizer.logoutSuccessUrl("/"));
            
        http.oauth2Login(oauth2LoginCustomizer -> 
        				  oauth2LoginCustomizer
        				  	.userInfoEndpoint(userInfoEndpointCustomizer -> 
        				  						userInfoEndpointCustomizer
        				  							.userService(customOAuth2UserService)));
    	
    	return http.build();
    } //
    
}