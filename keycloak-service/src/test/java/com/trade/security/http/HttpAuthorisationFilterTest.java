package com.trade.security.http;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.trade.JUnitTag.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@Tag(UNIT_TEST)
@ExtendWith(MockitoExtension.class)
public class HttpAuthorisationFilterTest {

	@Mock
	private HttpAuthorisationService httpAuthorisationService;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private FilterChain filterChain;

	@InjectMocks
	private HttpAuthorisationFilter filter;

	@Test
	public void testAuthorised() throws ServletException, IOException {
		filter.doFilter(request, response, filterChain);
		verify(httpAuthorisationService).authorise(request, response);
		verify(filterChain).doFilter(request, response);
	}

	@Test
	public void testUnauthorised() throws ServletException {
		doThrow(new ServletException()).when(httpAuthorisationService).authorise(request, response);
		assertThrows(ServletException.class, () -> {
			filter.doFilter(request, response, filterChain);
		});
	}
}
