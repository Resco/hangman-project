package it.xpug.hangman.main;

public class PlayerSession {
	
	private String id_session;
	private String id_player;

	public PlayerSession(String id_session, String id_player) {
		this.id_session = id_session;
		this.id_player = id_player;
	}

	public String id_session() {
		return id_session;
	}
	
	public String id_player(){
		return id_player;
	}
}
