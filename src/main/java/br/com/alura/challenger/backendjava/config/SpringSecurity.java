package br.com.alura.challenger.backendjava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.alura.challenger.backendjava.security.SimpleURLAuthenticationSuccessHandler;

@Configuration
public class SpringSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()	
			.antMatchers("/webjars/**").permitAll()	
            .antMatchers("/importacao/**"  , "/usuarios/**", "/analise/**").hasAnyAuthority("COMUM","TODAS")
			.anyRequest().denyAll()
			
			.and()
				.formLogin()
					.loginPage("/").defaultSuccessUrl("/importacao")
					.successHandler(simpleURLAuthenticationSuccessHandler())
					.permitAll()				
				// .and()
				// 	.exceptionHandling()
				// 	.accessDeniedPage("/403")					
				.and()
					.logout()
					.logoutSuccessUrl("/?logout").permitAll()
				.and()
					.sessionManagement()
						.maximumSessions(1)
					
			;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}

    @Bean
	public AuthenticationSuccessHandler simpleURLAuthenticationSuccessHandler(){
		return new SimpleURLAuthenticationSuccessHandler();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		 auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(5);
	}
}
