package it.xpug.hangman.main;

public class PlayerSession {
	
	private String session_id;
	private String player_id;

	public PlayerSession(String id_session, String id_player) {
		this.session_id = id_session;
		this.player_id = id_player;
	}

	public String session_id() {
		return session_id;
	}
	
	public String player_id(){
		return player_id;
	}
}
