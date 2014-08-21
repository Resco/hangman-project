package it.xpug.hangman.main;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import it.xpug.generic.db.Database;

import java.util.Random;

public class GamesRepository {
	private Database database;
	private Random random;

	public GamesRepository(Database database) {
		this.database = database;
		this.random = new Random();
	}
	
	public Game createGame(String nickname) {
		String sql = "insert into games (num, player, points, code)" +
				" values (?, ?, ?, ?)";
		String number = valueOf(abs(random.nextLong()));
		String code = codemaker();
		database.execute(sql, number, nickname,
				"0", code);
		Game game = new Game(number, nickname, "0", code);
		return game;
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
