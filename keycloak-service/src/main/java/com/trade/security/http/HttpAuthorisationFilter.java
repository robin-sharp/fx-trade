package com.trade.security.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An HttpFilter that checks an HttpRequest is authorised by caling to the configured SecuritySevice.
 * If the caller is not authorised a ServletException is thrown.
 */
@Component
public class HttpAuthorisationFilter extends GenericFilterBean {

	@Autowired
	private HttpAuthorisationService httpAuthorisationService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		httpAuthorisationService.authorise((HttpServletRequest) request, (HttpServletResponse) response);
		filterChain.doFilter(request, response);
	}
}
