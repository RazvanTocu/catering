package com.example.demo.authentication;

import static com.example.demo.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired DataSource datasource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			//authorization paragraph: qui definiamo chi puo accedere a cosa
			.authorizeRequests()
			//chiunque (autenticato o no) puo' accedere alle pagine index, login, register, ai css e alle immagini
			.antMatchers(HttpMethod.GET, "/","/img/**" ,"/index", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
			//chiunque (autenticato o no) puo' mandare richieste POST al punto di accesso
			.antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
			//solo gli utenti autenticati con ruolo admin possono accedere a risorse con path /admin/**
			.antMatchers(HttpMethod.GET, "/**/add/**","/**/edit/**","/**/delete/**").hasAnyAuthority(ADMIN_ROLE)
			.antMatchers(HttpMethod.POST, "/**/add/**","/**/edit/**","/**/delete/**").hasAnyAuthority(ADMIN_ROLE)
			.anyRequest().authenticated() // se io tolgo questo , non serve l'autenticazione per tutto il resto
		
			//login paragraph: qui definiamo come è gestita l'autenticazione
			//usiamo il protocollo formlogin
			.and().formLogin()
			//la pagina di login si trova a /login
			//NOTA: Spring gestisce il post di login automaticamente
			.loginPage("/login")
			//se il login ha successo, si viene rediretti al path /default
			.defaultSuccessUrl("/default")
			
			.and().oauth2Login()
			
			.loginPage("/login")
			
			.defaultSuccessUrl("/")

			//logout paragraph: qui definiamo il logout
			.and().logout()
			//il logout è attivato con una richiesta GET a "/logout"
			.logoutUrl("/logout")
			//in caso di successo, si viene reindirizzati all'index
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.clearAuthentication(true).permitAll();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
		    //per accedere alle credenziali salvate
			.dataSource(this.datasource)
			//ricpueriamo username e ruolo
			.authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?")
			//ricuperiamo username, password e un flag
			.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
