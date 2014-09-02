package it.xpug.hangman.main;

import java.security.MessageDigest;
import java.util.HashMap;

import it.xpug.generic.db.Database;
import it.xpug.generic.db.ListOfRows;

public class PlayersRepository {
	private Database database;

	public PlayersRepository(Database database) {
		this.database = database;
	}

	public void add(Player player) {
		String sql = "insert into players (player_id, mail, salt, cript, num_games, average)" +
				" values (?, ?, ?, ?, ?, ?)";
		database.execute(sql, player.player_id(), player.mail(),
				player.salt(), player.encryptedPassword(), 0, 0);
	}

	public boolean nicknameExists(String nick) {
		String sql = "select * from players where player_id = ?";
		ListOfRows rows = database.select(sql, nick);
		return rows.size() != 0;
	}

	public boolean correctPassword (String nick, String password){
		String sql = "select * from players where player_id = ?";
		ListOfRows rows = database.select(sql, nick);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String salt = (String) row.get("salt");
		String calculated = encryptedPassword(password, salt);
		String encr = (String) row.get("cript");
		return (calculated.equals(encr));
	}


	private String encryptedPassword(String password, String salt) {
		try {
			String seed = "" + password + salt;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(seed.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return Player.toHexString(digest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void add_finished_game(String player_id, int total_score) {
		//aumenta il num_games e ricalcola la media
		//prendo il numero di partite finite
		String sql = "select * from players where player_id = ?";
		ListOfRows rows = database.select(sql, player_id);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		int num_games = (Integer) row.get("num_games");
		num_games = num_games +1;
		String sql2 = "update players set num_games = ? where player_id = ?";
		database.execute(sql2, num_games, player_id);
		float new_average = ((float)total_score) / ((float)num_games);
		String sql3 = "update players set average = ? where player_id = ?";
		database.execute(sql3, new_average, player_id);
		
	}

	public float get_average(String nick) {
		String sql = "select * from players where player_id = ?";
		ListOfRows rows = database.select(sql, nick);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		float average = (Float) row.get("average");
		return average;
	}

	public int get_games(String nick) {
		String sql = "select * from players where player_id = ?";
		ListOfRows rows = database.select(sql, nick);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		int games = (Integer) row.get("num_games");
		return games;
	}
}
