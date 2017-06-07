package gov.divine.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
				.antMatchers("/exchanger", "/exchanger/error", "/exchanger/login", "/exchanger/registration").permitAll()
				.anyRequest().authenticated().and().csrf().disable()
				.formLogin()
					.loginPage("/exchanger/login").failureUrl("/exchanger/login?error=true")
					.defaultSuccessUrl("/exchanger/main", true)
					.usernameParameter("login")
					.passwordParameter("password")
				.and().logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/exchanger/logout"))
					.logoutSuccessUrl("/exchanger/login")
				.and().sessionManagement()
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
					.expiredUrl("/exchanger/login")
					.sessionRegistry(sessionRegistry());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public SessionRegistry sessionRegistry(){
    	return new SessionRegistryImpl();
	}

	@Bean
	public ServletListenerRegistrationBean httpSessionEventPublisher() {
	    return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

}
