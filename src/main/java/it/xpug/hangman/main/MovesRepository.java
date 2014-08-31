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
	
	public Move createMove(String game_id, String player_id, String move, String answer, String code) {
		String sql2 = "select * from moves where num_game = ?";
		ListOfRows rows = database.select(sql2, game_id);
		int size = rows.size() + 1;
		String sql = "insert into moves (num_game, player, num_move, move, code)" +
				" values (?, ?, ?, ?, ?)";
		database.execute(sql, game_id, player_id,
				size , move, code);
		Move mov = new Move(game_id, player_id, size, move, answer);
		return mov;
	}
}
