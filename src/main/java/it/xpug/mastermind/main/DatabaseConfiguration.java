package it.xpug.mastermind.main;

import java.sql.Connection;

public interface DatabaseConfiguration {
	Connection getConnection();
}
