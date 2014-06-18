package it.xpug.supermarket.main;

import static java.lang.Math.*;
import static java.lang.String.*;

import java.util.*;

public class SessionsRepository {

	private Random random;
	private List<CashierSession> sessions = new ArrayList<CashierSession>();

	public SessionsRepository(Random random) {
		this.random = random;
	}

	public CashierSession createSession() {
		CashierSession session = new CashierSession(valueOf(abs(random.nextLong())));
		sessions.add(session);
		return session;
	}

	public CashierSession findSession(String sessionId) {
		for (CashierSession session : sessions) {
			if (session.id().equals(sessionId))
				return session;
		}
		return null;
	}

}
