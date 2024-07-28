package com.oss.unist.hr.security;


import com.oss.unist.hr.model.enums.Role;
import com.oss.unist.hr.service.LoginService;
import com.oss.unist.hr.service.implementation.LoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;


/*
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {

		return new UserDetailsServiceImpl();
	}

	@Bean
	public LoginService loginService() {

		return new LoginServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService());

		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorization -> authorization
				.requestMatchers(new AntPathRequestMatcher("/dashboard")).authenticated()
				.anyRequest().permitAll());

		http.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login-user")
				.usernameParameter("email")
				.permitAll()
				.successHandler(successHandler(loginService()))
		);

		http.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler(LoginService loginService) {
		return (request, response, authentication) -> {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

			if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
				response.sendRedirect("/admins");
			} else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
				loginService.processLogin(getData(userDetails), request.getSession(), null, Role.USER);
				response.sendRedirect("/dashboard");
			}
		};
	}

	private String getData(MyUserDetails userDetails){
		return userDetails.getUsername();
	}

}*/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {

		return new UserDetailsServiceImpl();
	}

	@Bean
	public LoginService loginService() {

		return new LoginServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService());

		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorization -> authorization
				.requestMatchers(new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/login/**"), new AntPathRequestMatcher("/register"), new AntPathRequestMatcher("/register/**"), new AntPathRequestMatcher("/")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/css/**"), new AntPathRequestMatcher("/js/**"), new AntPathRequestMatcher("/images/**"), new AntPathRequestMatcher("/fonts/**"), new AntPathRequestMatcher("/webjars/**")).permitAll()
				.anyRequest().authenticated());


		http.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/execute-login")
				.usernameParameter("email")
				.permitAll()
				.successHandler(successHandler(loginService()))
		);

		http.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler(LoginService loginService) {
		return (request, response, authentication) -> {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

			if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
				loginService.processLogin(getData(userDetails), request.getSession(), null, Role.ADMIN, null);
				response.sendRedirect("/admin/users");
			} else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
				loginService.processLogin(getData(userDetails), request.getSession(), null, Role.USER, null);
				response.sendRedirect("/dashboard");
			}
		};
	}

	private String getData(MyUserDetails userDetails){
		return userDetails.getUsername();
	}


}
