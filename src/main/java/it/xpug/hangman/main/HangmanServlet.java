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
		PlayersRepository p_repository = new PlayersRepository(database);
		SessionsRepository s_repository = new SessionsRepository(new Random());
		
		RegistrationController r_controller = new RegistrationController(p_repository, request, response);
		AuthenticationController a_controller = new AuthenticationController(p_repository, s_repository, request, response);
		if (request.getRequestURI().equals("/register")) {
			r_controller.service();
			return;
		}
		
		if (request.getRequestURI().equals("/authenticate")) {
			a_controller.service();
			return;
		}
		
		if (request.getRequestURI().equals("/logged")) {
			a_controller.logged_service();
			return;
		}
	}
}
