package it.xpug.hangman.main;

public class Move {
	private String id_game;
	private String id_player;
	private int id_move;
	private String move;
	private String result;
	
	public Move (String id_game, String id_player, int id_move,
			String move, String result){
		this.id_game=id_game;
		this.id_player=id_player;
		this.id_move=id_move;
		this.move=move;
		this.result=result;
	}

	public String id_game(){
		return this.id_game;
	}
	
	public String id_player(){
		return this.id_player;
	}
	
	public int id_move(){
		return this.id_move;
	}
	
	public String move(){
		return this.move;
	}
	
	public String result(){
		return this.result;
	}
}
