package it.xpug.hangman.main;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RankingController extends Controller{
	
	private PlayersRepository p_repository;
	private GamesRepository g_repository;
	
	public RankingController(PlayersRepository p_rep, GamesRepository g_rep, HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		this.p_repository = p_rep;
		this.g_repository = g_rep;
	}
	
	public void service() throws IOException {
		//devo prendere i dati dal db e mandarli
		writeBody(toJson("description", "Nice job! You just registered to Hangman"));
	}

}
