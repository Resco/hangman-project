package it.xpug.hangman.main;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SessionsRepository {

	private Random random;
	private List<PlayerSession> sessions = new ArrayList<PlayerSession>();

	public SessionsRepository(Random random) {
		this.random = random;
	}

	public PlayerSession createSession(String id_player) {
		PlayerSession session = new PlayerSession(valueOf(abs(random.nextLong())), id_player);
		sessions.add(session);
		return session;
	}

	public PlayerSession findSession(String sessionId) {
		for (PlayerSession session : sessions) {
			if (session.id().equals(sessionId))
				return session;
		}
		return null;
	}

}
