package it.xpug.hangman.main;

import it.xpug.generic.db.Database;
import it.xpug.generic.db.ListOfRows;

public class PlayersRepository {
	private Database database;

	public PlayersRepository(Database database) {
		this.database = database;
	}
	
	public void add(Player player) {
		String sql = "insert into players (nickname, encrypted_password) values (?, ?)";
		database.execute(sql, cashier.cashierId(), cashier.encryptedPassword());
	}

	public boolean cashierExists(Cashier cashier) {
		String sql = "select * from cashiers where id = ? and encrypted_password = ?";
		ListOfRows rows = database.select(sql, cashier.cashierId(), cashier.encryptedPassword());
		return rows.size() != 0;
	}

	public long count() {
		String sql = "select count(*) as cashiers_count from cashiers";
		return (Long) database.selectOneValue(sql, "cashiers_count");
	}
}
