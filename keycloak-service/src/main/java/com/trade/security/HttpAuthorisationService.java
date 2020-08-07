package com.trade.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpAuthorisationService {
	void authorise(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
