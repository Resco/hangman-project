package it.xpug.hangman.main;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationController extends Controller {
	private PlayersRepository p_repository;

	public AuthenticationController(PlayersRepository p_rep, HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		this.p_repository = p_rep;
	}
	
	public void service() throws IOException {
		String nick = request.getParameter("nickname");
		String pw = request.getParameter("password");
		if(!p_repository.nicknameExists(nick)){
			writeBody(toJson("description", "This nickname doesn't exist"));
		}
		else if(!p_repository.correctPassword(nick, pw)){
			writeBody(toJson("description", "Try another password"));
		}
		else
			writeBody(toJson("description", "You just logged in"));

	}
}
