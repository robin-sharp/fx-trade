package com.trade.entity.controller;

import com.trade.entity.Party;
import com.trade.security.http.HttpAuthorisationFilter;
import com.trade.security.http.HttpSecureMethodBuilder;
import com.trade.security.http.HttpSecureMethodStore;
import com.trade.security.oauth2.OAuth2SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

//@Validated
@Configuration
//@ConfigurationProperties("keycloak")
public class EntitySecurityConfiguration {

	//@NotEmpty
	private String webservice;

	@Autowired
	public void configureMethods(HttpSecureMethodStore httpSecureMethodStore) {
		httpSecureMethodStore.addHttpSecureMethods("entity-client",
				new HttpSecureMethodBuilder().buildHttpSecureMethods(PartyController.class, Party.class));
	}

	@Autowired
	public void configureFilter(OAuth2SecurityConfiguration oAuth2SecurityConfiguration, HttpAuthorisationFilter httpAuthorisationFilter) {
		oAuth2SecurityConfiguration.addFilter(httpAuthorisationFilter);
	}

	public String getWebservice() {
		return webservice;
	}

	public void setWebservice(String webservice) {
		this.webservice = webservice;
	}
}