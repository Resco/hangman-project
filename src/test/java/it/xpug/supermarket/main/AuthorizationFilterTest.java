package it.xpug.supermarket.main;

import static org.junit.Assert.*;

import java.util.*;

import javax.servlet.http.*;

import org.junit.*;

public class AuthorizationFilterTest {
	WebRequest request = new WebRequest();
	WebResponse response = new WebResponse();
	SessionsRepository sessions = new SessionsRepository(new Random(12345));
	AuthorizationFilter filter = new AuthorizationFilter(sessions, request, response);

	@Test
	public void notAuthorizedNoCookie() throws Exception {
		assertFalse("no cookie", filter.canContinue());
		assertEquals(401, response.getStatus());
		assertEquals("{ \"description\": \"Please go to /authenticate\" }", response.getBody());
	}

	@Test
	public void notAuthorizedBadId() throws Exception {
		request.addCookie(new Cookie("session_id", "bad id"));
		assertFalse("bad id", filter.canContinue());
	}

	@Test
	public void authorized() throws Exception {
		CashierSession session = sessions.createSession();
		request.addCookie(new Cookie("session_id", session.id()));
		assertTrue("good cookie", filter.canContinue());
		assertEquals(0, response.getStatus());
		assertEquals("", response.getBody());
	}

}
