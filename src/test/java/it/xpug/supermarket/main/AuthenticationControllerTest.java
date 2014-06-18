package it.xpug.supermarket.main;

import static org.junit.Assert.*;
import it.xpug.generic.db.*;

import java.util.*;

import org.junit.*;

public class AuthenticationControllerTest {
	WebResponse response = new WebResponse();
	WebRequest request = new WebRequest();
	CashierRepository cashiers = new CashierRepository(
			new Database(
					new PropertyFileDatabaseConfiguration("database.properties")));

	SessionsRepository sessions = new SessionsRepository(new Random(123));
	AuthenticationController controller = new AuthenticationController(sessions, cashiers, request, response);

	@Test
	public void authenticationSucceeds() throws Exception {
		request.addParameter("id", "123");
		request.addParameter("password", "secret");
		controller.service();
		assertEquals(200, response.getStatus());
		assertEquals("{ \"session_id\": \"5106534569952410475\" }", response.getBody());
		assertEquals("5106534569952410475", response.getCookie("session_id").getValue());
	}

	@Test
	public void authenticationFailsBadId() throws Exception {
		request.addParameter("id", "888");
		request.addParameter("password", "secret");
		controller.service();
		assertEquals(401, response.getStatus());
		assertEquals("{ \"description\": \"Bad id or password\" }", response.getBody());
	}

	@Test
	public void authenticationFailsBadPassword() throws Exception {
		request.addParameter("id", "123");
		request.addParameter("password", "wrongpassword");
		controller.service();
		assertEquals(401, response.getStatus());
	}

}
