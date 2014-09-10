package it.xpug.mastermind.main;

public class Move {
	private String game_id;
	private String player_id;
	private int move_id;
	private String move;
	private String result;
	
	public Move (String id_game, String id_player, int id_move,
			String move, String result){
		this.game_id=id_game;
		this.player_id=id_player;
		this.move_id=id_move;
		this.move=move;
		this.result=result;
	}

	public String game_id(){
		return this.game_id;
	}
	
	public String player_id(){
		return this.player_id;
	}
	
	public int move_id(){
		return this.move_id;
	}
	
	public String move(){
		return this.move;
	}
	
	public String result(){
		return this.result;
	}
}
