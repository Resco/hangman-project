package it.xpug.hangman.main;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import it.xpug.generic.db.Database;
import it.xpug.generic.db.ListOfRows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SessionsRepository {

	private Random random;
	private Database database;
	
	public SessionsRepository(Database database, Random random) {
		this.database = database;
		this.random = random;
	}

	public PlayerSession createSession(String id_player) {
		PlayerSession session = new PlayerSession(valueOf(abs(random.nextLong())), id_player);
		String sql = "insert into sessions (player_id, session_id) values (?, ?)";
		database.execute(sql, session.id_player(), session.id_session());
		return session;
	}

	public PlayerSession findSession(String sessionId) {
		try{String sql = "select * from sessions where session_id = ?";
		ListOfRows rows = database.select(sql, sessionId);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String ses_id = (String) row.get("session_id");
		String play_id = (String) row.get("player_id");
		PlayerSession returned = new PlayerSession(ses_id, play_id);
		return returned;}
		catch(IndexOutOfBoundsException exc){
			return null;
		}
	}
	
	

}
