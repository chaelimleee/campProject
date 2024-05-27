package com.javateam.campProject.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.javateam.campProject.domain.RoleEnum;
import com.javateam.campProject.service.CustomOAuth2UserService;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    
    // security 적용 예외 URL 등록
    // Swagger 페이지 접근에 대한 예외 처리
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    	
    	return (web) -> web.ignoring().requestMatchers("/", "/thumb_sub4_ext/**", "/css/**", "/webjars/**", 
										"/images/**", "/img/**", "/js/**",
										"/summernote/**","/jquery/**","/axios/**", "/lib/**","/vendor/**", "/profile");
    }

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    	http.csrf(csrfCustomizer -> csrfCustomizer.disable());
    	
        http.headers(headersCustomizer -> headersCustomizer
        				.frameOptions(Customizer.withDefaults()).disable());
        
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> 
								authorizeHttpRequestsCustomizer
								//.requestMatchers("/api/v3/**").hasRole(RoleEnum.USER.name())
								.requestMatchers("/", "/css/**", "/webjars/**","/img/**","/vendor/**",
										"/images/**", "/js/**", "/lib/**").permitAll()
								.requestMatchers(
										"/board/write.do",
					    				"/board/writeProc.do",
					    				"/board/update_boardPass.do",
					    				"/board/update.do", 
					    				"/board/updateProc.do",
					    				"/board/deleteProc.do",
					    				"/board/delete_boardPass.do",
						        		"/board/view.do/**",
						        		"/board/list.do",
						        		"/board/searchList.do",
						        		"/board/searchList.do/**",
						                "/board/image", 
						                "/board/image/**",
						                "/board/getRepliesAll.do",
						                "/board/download/**",
						                "/board/replyWrite.do"        						                
						        		).permitAll()
								.requestMatchers(
										"/member/reservationView.do/**",
					    				"/reservation.do",
					                    "/board/replyWrite.do",
					                    "/board/replyUpdate.do",
					                    "/board/replyDelete.do",
					                    "/board/checkLock.do"
					    				).authenticated()
								
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