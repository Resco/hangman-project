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
		SessionsRepository s_repository = new SessionsRepository(database, new Random());
		GamesRepository g_repository = new GamesRepository(database);
		MovesRepository m_repository = new MovesRepository(database);
		RegistrationController r_controller = new RegistrationController(p_repository, request, response);
		AuthenticationController a_controller = new AuthenticationController(p_repository, s_repository, request, response);
		RankingController rank_controller = new RankingController(p_repository, g_repository, request, response);
		GameController g_controller = new GameController(p_repository, s_repository, m_repository, request, g_repository, response);
		if (request.getRequestURI().equals("/register")) {
			r_controller.service();
			return;
		}
		
		if (request.getRequestURI().equals("/authenticate")) {
			a_controller.service();
			return;
		}
		
		if (request.getRequestURI().equals("/logged")) {
			a_controller.cookie_service();
			return;
		}
		
		if (request.getRequestURI().equals("/newgame")) {
			g_controller.new_game_service();
			return;
		}
		
		if (request.getRequestURI().equals("/move")) {
			g_controller.move_service();
			return;
		}
		
		if (request.getRequestURI().equals("/g_rank")) {
			rank_controller.service();
			return;
		}
		
		if (request.getRequestURI().equals("/p_hist")) {
			rank_controller.service_player();
			return;
		}
		
		if (request.getRequestURI().equals("/logout")) {
			a_controller.logout_service();
			return;
		}
	}
}
