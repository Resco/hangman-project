package it.xpug.hangman.main;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import it.xpug.generic.db.Database;
import it.xpug.generic.db.ListOfRows;

import java.sql.Timestamp;
import java.util.Date;
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
		Timestamp started = new Timestamp(new Date().getTime());
		String sql = "insert into games (game_id, player_id, started, score, code)" +
				" values (?, ?, ?, ?, ?)";
		String number = valueOf(abs(random.nextLong()));
		String code = codemaker();
		database.execute(sql, number, nickname, started,
				0, code);
		Game game = new Game(number, nickname, 0, code);
		return game;
	}
	
	public String find_game_code(String game_id){
		String sql = "select * from games where game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String game_code = (String) row.get("code");
		return game_code;
		
	}
	
	public String find_game_player(String game_id){
		String sql = "select * from games where game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String player_id = (String) row.get("player_id");
		return player_id;
		
	}
	
	public int find_game_score(String game_id){
		String sql = "select * from games where game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		int score = (Integer) row.get("score");
		return score;
		
	}
	
	private String codemaker(){
		String code = "";
		for(int i=0; i<4; i++){
			int digit = 1 + random.nextInt(6);
			code = code + digit;
		}
		return code;
		
	}

	public void set_finished(String parameter) {
		Timestamp finished = new Timestamp(new Date().getTime());
		String sql = "update games set finished = ? where game_id = ?";
		database.execute(sql, finished, parameter);
		
	}

	public void set_score(String parameter) {
		int score = find_game_score(parameter);
		score = score + 1;
		String sql = "update games set score = ? where game_id = ?";
		database.execute(sql, score, parameter);

	}

	public int total_score(String player_id) {
		//calcola il totale dei punti su tutte le partite di un player
		String sql = "select * from games where player_id = ?";
		ListOfRows rows = database.select(sql, player_id);
		int size = rows.size();
		int total = 0;
		for (int i=0;i<size;i++){
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(i);
		int score = (Integer) row.get("score");
		total = total + score;
		}
		return total;
	}

	public ListOfRows select_player_games(String nickname) {
		String sql = "select * from games where player_id = ? order by started";
		ListOfRows rows = database.select(sql, nickname);
		return rows;
	}
}
