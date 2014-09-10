package it.xpug.mastermind.main;

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
		//crea e inserisce in db una partita
		
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
		//restituisce il codice da indovinare di una partita,
		//dato l'id della partita
		
		String sql = "select * from games where game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String game_code = (String) row.get("code");
		return game_code;
		
	}
	
	public String find_game_player(String game_id){
		//restituisce l'id del player di una partita,
		//dato l'id della partita
		
		String sql = "select * from games where game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String player_id = (String) row.get("player_id");
		return player_id;
		
	}
	
	public int find_game_score(String game_id){
		//restituisce il punteggio di una partita,
		//dato l'id della partita
		
		String sql = "select * from games where game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		int score = (Integer) row.get("score");
		return score;
		
	}
	
	private String codemaker(){
		//metodo che crea le stringhe a 4 cifre
		
		String code = "";
		for(int i=0; i<4; i++){
			int digit = 1 + random.nextInt(6);
			code = code + digit;
		}
		return code;
		
	}

	public void set_finished(String game_id) {
		//setta come terminata una partita, dao l'id della partita
		
		Timestamp finished = new Timestamp(new Date().getTime());
		String sql = "update games set finished = ? where game_id = ?";
		database.execute(sql, finished, game_id);
		
	}

	public void set_score(String id_game) {
		//incrementa di uno il punteggio di una partita in corso dopo una
		//mossa, dato l'id della partita
		int score = find_game_score(id_game);
		score = score + 1;
		String sql = "update games set score = ? where game_id = ?";
		database.execute(sql, score, id_game);

	}

	public int total_score(String player_id) {
		//calcola e restituisce il totale dei punti su tutte le partite 
		//di un player,dato l'id del player 
	 
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
		//restituisce l'elenco delle partite di un player,
		//dato l'id del player
		
		String sql = "select * from games where player_id = ? order by started";
		ListOfRows rows = database.select(sql, nickname);
		return rows;
	}
}
