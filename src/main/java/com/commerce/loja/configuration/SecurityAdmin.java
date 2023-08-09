package com.commerce.loja.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.commerce.loja.enums.RoleEnum;
import com.commerce.loja.model.Role;
import com.commerce.loja.service.EmployeeUserDetailsService;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityAdmin{
	
	//private static final String ADMIN = Role.class.getName();

	@Autowired
	private EmployeeUserDetailsService employeeUserDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests( (authorize) -> authorize
					
					.requestMatchers("/assets/**").permitAll()
					.requestMatchers("/js/**").permitAll()
					.requestMatchers("/css/**").permitAll()
					.anyRequest().authenticated() )
			
					.formLogin()
						.loginPage("/login")
						.defaultSuccessUrl("/administrativo")
						.permitAll();
					
			 
		return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();   
    } 
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, EmployeeUserDetailsService employeeUserDetailsService) throws Exception{
		
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(employeeUserDetailsService)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
	}
	
	
	

	 


		
}
