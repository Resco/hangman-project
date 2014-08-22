package it.xpug.hangman.main;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import it.xpug.generic.db.Database;
import it.xpug.generic.db.ListOfRows;

import java.util.HashMap;
import java.util.Random;

public class GamesRepository {
	private Database database;
	private Random random;

	public GamesRepository(Database database) {
		this.database = database;
		this.random = new Random();
	}
	
	public Game createGame(String nickname) {
		String sql = "insert into games (game_id, player, points, code)" +
				" values (?, ?, ?, ?)";
		String number = valueOf(abs(random.nextLong()));
		String code = codemaker();
		database.execute(sql, number, nickname,
				"0", code);
		Game game = new Game(number, nickname, "0", code);
		return game;
	}
	
	public String find_game_code(String game_id){
		String sql = "select * from games where game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String game_code = (String) row.get("code");
		return game_code;
		
	}
	
	private String codemaker(){
		String code = "";
		for(int i=0; i<4; i++){
			int digit = 1 + random.nextInt(6);
			code = code + digit;
		}
		return code;
		
	}
}
