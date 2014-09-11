package it.xpug.mastermind.main;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import it.xpug.generic.db.Database;
import it.xpug.generic.db.ListOfRows;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class SessionsRepository {

	private Random random;
	private Database database;
	
	public SessionsRepository(Database database, Random random) {
		this.database = database;
		this.random = random;
	}

	public PlayerSession createSession(String id_player) {
		//crea e inserisce in db una sessione
		
		Timestamp started = new Timestamp(new Date().getTime());
		PlayerSession session = new PlayerSession(valueOf(abs(random.nextLong())), id_player);
		String sql = "insert into sessions (player_id, session_id, started) values (?, ?, ?)";
		database.execute(sql, session.player_id(), session.session_id(), started);
		return session;
	}

	public PlayerSession findSession(String sessionId) {
		//trova la sessione in base all'id
		
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

	public void delete_session(String parameter) {
		//cancella una sessione
		
		String sql = "delete from sessions where session_id = ?";
		database.execute(sql, parameter);
		
	}

	
	
	

}
