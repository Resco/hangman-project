package it.xpug.hangman.main;

import java.sql.Connection;

public interface DatabaseConfiguration {
	Connection getConnection();
}
