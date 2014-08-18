package it.xpug.hangman.main;

import it.xpug.generic.db.Database;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HangmanServlet extends HttpServlet {

	private DatabaseConfiguration configuration;

	public HangmanServlet(DatabaseConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database database = new Database(configuration);
		
	}
}
