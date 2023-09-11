package com.ssamz.jblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ssamz.jblog.security.OAuth2UserDetailsServiceImpl;
import com.ssamz.jblog.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class NBlogWebSecurityConfiguration {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private OAuth2UserDetailsServiceImpl oauth2UserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 사용자가 입력한 username으로 User 객체를 검색하고 password를 비교한다.
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	protected SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
		
//		// 인증 없이 접근을 허용하는 경로
//    	// "/webjars/**", "/js/**", "/image/**", "/", "/auth/**"
		http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll().
						requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/image/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/oauth/**")).permitAll()
						.anyRequest().authenticated());
		http.formLogin(login -> login
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/securitylogin")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/"));
		http.logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/"));
		http.oauth2Login();
		return http.build();
	}
}