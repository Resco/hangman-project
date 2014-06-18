package it.xpug.supermarket.main;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class SessionsRepositoryTest {
	SessionsRepository sessions = new SessionsRepository(new Random(1234));

	@Test
	public void createSessions() {
		assertEquals("6519408338692630574", sessions.createSession().id());
	}

	@Test
	public void findSession() throws Exception {
		CashierSession session0 = sessions.createSession();
		CashierSession session1 = sessions.createSession();

		assertNull("nonexistent", sessions.findSession("abc"));
		assertEquals(session0, sessions.findSession(session0.id()));
		assertEquals(session1, sessions.findSession(session1.id()));
	}

}
