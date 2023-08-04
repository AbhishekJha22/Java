package com.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.inMemoryAuthentication()
	 * .withUser("admin").password("admin123").roles("USER"); }
	 */
	@Autowired
	DataSource dataSource;
	
	
	@Bean 
	public PasswordEncoder getPasswordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/products/modify/**").hasRole("ADMIN")
		.and()
		.formLogin()
		.loginPage("/login") // Specifies the login page URL
	    .loginProcessingUrl("/signin")
	    .usernameParameter("username")  
	    .passwordParameter("password")
	    .defaultSuccessUrl("/") 
	    .failureHandler((req,res,exp)->{  
					/*
					 * String errMsg="Invalid Credentials"; req.getSession().setAttribute("message",
					 * errMsg);
					 */
	        res.sendRedirect("/loginerror"); 
	      })
	    .permitAll() 
	    .and()
	    .logout()
	    .logoutUrl("/signout")		
	    .logoutSuccessHandler((req,res,auth)->{   
	         req.getSession().setAttribute("message", "You are logged out successfully.");
	         res.sendRedirect("/login");
	      })
	      .permitAll()
		  .and()
		  .csrf().disable() ;
		
		
		http.authorizeRequests().antMatchers("/products/get/**").hasAnyRole("USER", "ADMIN")
		.and()
		.formLogin()
		.loginPage("/login") // Specifies the login page URL
	    .loginProcessingUrl("/signin")
	    .usernameParameter("username")  
	    .passwordParameter("password")
	    .defaultSuccessUrl("/") 
	    .failureHandler((req,res,exp)->{  
					/*
					 * String errMsg="Invalid Credentials"; req.getSession().setAttribute("message",
					 * errMsg); res.sendRedirect("login");
					 */
	    	res.sendRedirect("/loginerror");
	      })
	    .permitAll() 
	    .and()
	    .logout()
	    .logoutUrl("/signout")		
	    .logoutSuccessHandler((req,res,auth)->{   
	         req.getSession().setAttribute("message", "You are logged out successfully.");
	         res.sendRedirect("/login");
	      })
	      .permitAll()
		  .and()
		  .csrf().disable() ;
	}
}
