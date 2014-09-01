package it.xpug.hangman.main;

public class Game {
	private String game_id;
	private String player_id;
	private int score;
	private String code;
	
	public Game (String id_game, String id_player, int score, String secret_code){
		this.game_id=id_game;
		this.player_id=id_player;
		this.score=score;
		this.code=secret_code;
	}
	
	public String game_id(){
		return this.game_id;
	}
	
	public String player_id(){
		return this.player_id;
	}
	
	public int score(){
		return this.score;
	}
	
	public String code(){
		return this.code;
	}
}
