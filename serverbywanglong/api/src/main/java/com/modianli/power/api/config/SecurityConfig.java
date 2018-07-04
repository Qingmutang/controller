package com.modianli.power.api.config;

import com.modianli.power.api.security.SimpleUserDetailsServiceImpl;
import com.modianli.power.domain.jpa.Permission;
import com.modianli.power.domain.jpa.Permission_;
import com.modianli.power.persistence.repository.jpa.GrantedPermissionRepository;
import com.modianli.power.persistence.repository.jpa.PermissionRepository;
import com.modianli.power.persistence.repository.jpa.RoleRepository;
import com.modianli.power.persistence.repository.jpa.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Inject
  private UserRepository userRepository;

  @Inject
  private RoleRepository roleRepository;

  @Inject
  private PermissionRepository permissionRepository;

  @Inject
  private GrantedPermissionRepository grantedPermissionRepository;

  @Inject
  private FindByIndexNameSessionRepository sessionRepository;

//    @Inject
//    private SessionRepository<? extends ExpiringSession> sessionRepository;

  @Override
  public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers(//
							   "/**/*.html",//
							   "/css/**",//
							   "/js/**",//
							   "/i18n/**",//
							   "/libs/**",//
							   "/img/**",//
							   "/**/*.ico");
  }

  /**
   * @see ConcurrentSessionControlAuthenticationStrategy todo delete max session
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
//        final SessionRepositoryFilter<? extends ExpiringSession> sessionRepositoryFilter = new SessionRepositoryFilter<>(
//            sessionRepository);
//        sessionRepositoryFilter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
	http
		//.addFilterBefore(sessionRepositoryFilter, ChannelProcessingFilter.class)

		// @formatter:off
		.cors().and()
		.csrf()
		.disable()
		.headers()
		.frameOptions()
		.disable()
		.and()
		.sessionManagement()
		.maximumSessions(2)
		.sessionRegistry(sessionRegistry())
       // @formatter:on
	;

//        http
//            .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http
//                .authorizeRequests()
//                .antMatchers("/api/authenticate", "/api/register", "/api/public/**")
//                .permitAll();
//
//        http.
//                authorizeRequests()
//                .antMatchers("/api/mgt/**")
//                .hasRole("ADMIN");
//
//        http.authorizeRequests()
//                .antMatchers("/api/**")
//                .authenticated();
	configureInterceptedUris(http);

	//configureInterceptedUris(http);
//        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(
//                userDetailsServiceBean());
//        http.apply(securityConfigurerAdapter);
	// .and()
	// .httpBasic()
	// .and()
	// .csrf()
	// .disable();
	// .formLogin().loginPage("/login").permitAll().and().logout()
	// .permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
	authManagerBuilder
		.userDetailsService(
			new SimpleUserDetailsServiceImpl(this.userRepository, this.permissionRepository, this.grantedPermissionRepository))
		.passwordEncoder(passwordEncoder())
	;
  }

  @Bean
  SpringSessionBackedSessionRegistry sessionRegistry() {
	return new SpringSessionBackedSessionRegistry(this.sessionRepository);
  }

  @Bean
  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
	return super.userDetailsServiceBean();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	return passwordEncoder;
  }

  @Bean("corsConfigurationSource")
  @Profile(value = {"!prod"})
  CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration configuration = new CorsConfiguration();
	//TODO change to  xxx.modianli.com
	configuration.setAllowedOrigins(Arrays.asList("*"));
	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
	configuration.setAllowCredentials(true);
	configuration.setAllowedHeaders(Arrays.asList("Content-Type", "x-auth-token", "x-xsrf-token"));
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/api/**", configuration);
	source.registerCorsConfiguration("/v2/api-docs", configuration);
	return source;
  }

  @Profile(value = {"prod"})
  @Bean("corsConfigurationSource")
  CorsConfigurationSource prodCorsConfigurationSource() {
	CorsConfiguration configuration = new CorsConfiguration();
	//TODO change to  xxx.modianli.com
	configuration.setAllowedOrigins(
		Arrays.asList("https://www.modianli.com", "https://backend.modianli.com", "https://supplier.modianli.com",
					  "https://bidding.modianli.com"));
	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
	configuration.setAllowCredentials(true);
	configuration.setAllowedHeaders(Arrays.asList("Content-Type", "x-auth-token", "x-xsrf-token"));
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/api/**", configuration);
	source.registerCorsConfiguration("/v2/api-docs", configuration);
	return source;
  }

  private void configureInterceptedUris(HttpSecurity http) throws Exception {

	if (logger.isDebugEnabled()) {
	  logger.debug("configuring intercepted uris... @@@@ ");
	}

	ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry interceptUrlRegistry = http
		.authorizeRequests()
		.antMatchers("/api/authenticate", "/api/signup", "/api/signin", "/api/password", "/api/enterprise/signin",
					 "/api/public/**")
		.permitAll();
//        http.
//                authorizeRequests()
//                .antMatchers("/api/mgt/**")
//                .hasRole("ADMIN")
//                .and();

	Sort sort = new JpaSort(Permission_.category, Permission_.position);
	List<Permission> perms = permissionRepository.findByActiveIsTrue(sort);

	for (Permission resource : perms) {
	  interceptUrlRegistry
		  .regexMatchers(HttpMethod.valueOf(resource.getRequestMethod()), resource.getRequestUri())
		  .hasAuthority(resource.getName());
	}

	interceptUrlRegistry
		.antMatchers("/api/**")
		.authenticated();
	interceptUrlRegistry
		.antMatchers("/**")
		.permitAll();

	if (logger.isDebugEnabled()) {
	  logger.debug("end of configureInterceptedUris@ ");
	}
  }

}
