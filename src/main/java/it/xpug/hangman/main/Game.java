package it.xpug.hangman.main;

public class Game {
	private String id_game;
	private String id_player;
	private String trial_number;
	private String secret_code;
	
	public Game (String id_game, String id_player, String trial_number, String secret_code){
		this.id_game=id_game;
		this.id_player=id_player;
		this.trial_number=trial_number;
		this.secret_code=secret_code;
	}
	
	public String id_game(){
		return this.id_game;
	}
	
	public String id_player(){
		return this.id_player;
	}
	
	public String trial_number(){
		return this.trial_number;
	}
	
	public String secret_code(){
		return this.secret_code;
	}
}
