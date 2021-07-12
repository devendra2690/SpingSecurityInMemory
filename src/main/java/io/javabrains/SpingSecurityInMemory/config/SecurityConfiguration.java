package io.javabrains.SpingSecurityInMemory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		    .withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication()
		    .withUser("admin").password("admin").roles("ADMIN");
		    
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests()
		    .antMatchers("/admin").hasRole("ADMIN")
		    .antMatchers("/user").hasAnyRole("USER","ADMIN")
		    .antMatchers("/").fullyAuthenticated()
		    .and().logout().clearAuthentication(true).logoutSuccessUrl("/logoutSuccess")
		    .and().exceptionHandling().accessDeniedPage("/unauthorized")
		    .and().formLogin();
		    
	}

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
