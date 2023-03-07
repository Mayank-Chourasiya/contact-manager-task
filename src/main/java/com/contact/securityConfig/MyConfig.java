package com.contact.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class MyConfig{
	
	
	@Bean
	public UserDetailsService getUserDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		
		http.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/**").permitAll().and().formLogin().disable().httpBasic().and().csrf().disable();
		
//		http
//        .authorizeRequests()
//            .requestMatchers("/public/**").permitAll()
//            .anyRequest().authenticated()
//            .and()
//        .formLogin()
//            .and()
//        .logout()
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/login?logout")
//            .and()
//        .csrf().disable();
		
		http.authenticationProvider(authenticationProvider());
		
		DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
		return defaultSecurityFilterChain;
	}
	
	
}
