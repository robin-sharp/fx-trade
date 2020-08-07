package com.trade.security.oauth2;

import com.trade.security.HttpAuthorisationService;
import com.trade.security.http.HttpAuthorisationFilter;
import com.trade.security.http.HttpSecureMethodStore;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.GenericFilterBean;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class OAuth2SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

	private List<GenericFilterBean> filters = new ArrayList();

	@Bean
	public HttpSecureMethodStore getHttpSecureMethodStore() {
		return new HttpSecureMethodStore();
	}

	@Bean
	public HttpAuthorisationService getHttpAuthorisationService() {
		return new OAuth2SecurityService();
	}

	@Bean
	public HttpAuthorisationFilter getHttpSecurityFilter() {
		return new HttpAuthorisationFilter();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	@Bean
	public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(
				new SessionRegistryImpl());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.csrf().disable();
		http.authorizeRequests()
				.anyRequest()
				.permitAll();
		filters.forEach(filter ->
				http.addFilterAfter(filter, KeycloakAuthenticatedActionsFilter.class));
	}

	public void addFilter(GenericFilterBean filter) {
		filters.add(filter);
	}
}