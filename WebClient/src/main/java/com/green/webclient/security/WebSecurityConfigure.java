package com.green.webclient.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.green.webclient.handlers.OnAuthenticationFailureHandler;
import com.green.webclient.security.CustomerDetailsServiceImpl;
import com.green.webclient.handlers.OnAuthenticationSuccessHandler;
import com.green.webclient.helpers.PasswordManager;
import com.green.webclient.services.CustomerOAuth2Service;

@Configuration()
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomerOAuth2Service customerOAuth2Servive;
	
	@Autowired
	private OnAuthenticationSuccessHandler onAuthenticationSuccess;
	
	@Autowired
	private OnAuthenticationFailureHandler failureHandler;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
//		System.out.println(passwordEncoder().toString());
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerDetailsServiceImpl();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		System.out.println("passwordEncoder = " + passwordEncoder().toString());
		System.out.println("getPasswordEncoder(123456) = " + PasswordManager.getBCrypPassword("123456"));
		authProvider.setUserDetailsService(userDetailsService());
		
		return authProvider;
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
		tokenRepo.setDataSource(dataSource);

		return tokenRepo;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/", "/register", "/css/**", "/fonts/**", "/images/**", "/js/**", "/webfonts/**","/authenError","/api/products/**","/canvas/**","/product-photos/**"
				,"/category/**","/products/**","/single/**","/product2/**","/category-photos/**","/requireLogin","/verify","/formLoginResources/**","/firstLogin",
				"/searchproducts").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/requireLogin").permitAll()
		.usernameParameter("email")
		.passwordParameter("password")
		.loginProcessingUrl("/dologin")
		.failureHandler(failureHandler)
		.successHandler(onAuthenticationSuccess)
		.defaultSuccessUrl("/")
//		Oauth2 Login
		
		.and().oauth2Login().loginPage("/login").permitAll()
		.userInfoEndpoint().userService(customerOAuth2Servive)
		.and().successHandler(onAuthenticationSuccess)
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/")
		.and()
		.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(3600);
	}
}
