package it.xpug.hangman.main;

public class PlayerSession {
	
	private String id;
	private String id_player;

	public PlayerSession(String id, String id_p) {
		this.id = id;
		this.id_player = id_p;
	}

	public String id() {
		return id;
	}
	
	public String id_player(){
		return id_player;
	}
}
