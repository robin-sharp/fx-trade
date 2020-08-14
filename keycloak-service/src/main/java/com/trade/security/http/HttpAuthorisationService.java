package com.trade.security.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for classes that authorise an HttpRequest and HttpResponse
 */
public interface HttpAuthorisationService {
	void authorise(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
