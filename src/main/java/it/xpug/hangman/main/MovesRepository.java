package it.xpug.hangman.main;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;

import java.util.Random;

import it.xpug.generic.db.Database;
import it.xpug.generic.db.ListOfRows;

public class MovesRepository {
	private Database database;
	
	public MovesRepository(Database database) {
		this.database = database;
	}
	
	public Move createMove(String game_id, String player_id, String move, String result) {
		String sql2 = "select * from moves where game_id = ?";
		ListOfRows rows = database.select(sql2, game_id);
		int size = rows.size() + 1;
		String sql = "insert into moves (game_id, player_id, move_id, move, result)" +
				" values (?, ?, ?, ?, ?)";
		database.execute(sql, game_id, player_id,
				size + "" , move, result);
		Move mov = new Move(game_id, player_id, size, move, result);
		return mov;
	}
}
