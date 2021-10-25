package sg.edu.iss.jam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailServiceImplementation();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()

			.antMatchers("/api/**").permitAll()
			.antMatchers("/images/**","/css/**","/videos/**","/login/**","/login/signup","/login/save", "/media/**","/forgot_password","/reset_password").permitAll()
			.antMatchers("/login" , "/video/viewartistvideochannel/**", "/music/viewartistmusicchannel/**").permitAll()
			.antMatchers("/video/watchvideo/**", "/music/loadnextmusic/**").permitAll()
			.antMatchers("/video/genericvideolandingpage","/music/genericmusiclandingpage").permitAll()
			.antMatchers("/music/loadpreviousmusic/**", "/music/loadnextmusic/**").permitAll()
			.antMatchers("/music/listenmusic/**", "/video/medianotfound/**", "/music/medianotfound/**").permitAll()
			.antMatchers("/dashboard", "/home/subscribers/**", "/artist/**").hasAnyAuthority("Artist")
			.antMatchers("/search**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login/")
				.usernameParameter("email")
				.permitAll()
				.defaultSuccessUrl("/home/")
			.and()
			.csrf().disable().cors().and()
			.logout().logoutSuccessUrl("/").permitAll()
			;
	}
	
	
}
