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
		String sql = "insert into players (nickname, mail, salt, cript, n_partite, average)" +
				" values (?, ?, ?, ?, ?, ?)";
		database.execute(sql, player.playerNick(), player.playerMail(),
				player.playerSalt(), player.encryptedPassword(), 0, 0);
	}

	public boolean nicknameExists(String nick) {
		String sql = "select * from players where nickname = ?";
		ListOfRows rows = database.select(sql, nick);
		return rows.size() != 0;
	}

	public boolean correctPassword (String nick, String password){
		String sql = "select * from players where nickname = ?";
		ListOfRows rows = database.select(sql, nick);
		HashMap<String, Object> row = (HashMap<String, Object>) rows.get(0);
		String salt = (String) row.get("salt");
		String calculated = encryptedPassword(password, salt);
		String encr = (String) row.get("cript");
		return (calculated.equals(encr));
	}

	public long count() {
		String sql = "select count(*) as cashiers_count from cashiers";
		return (Long) database.selectOneValue(sql, "cashiers_count");
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
}
