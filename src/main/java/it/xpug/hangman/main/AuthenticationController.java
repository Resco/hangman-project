package it.xpug.hangman.main;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationController extends Controller {
	private PlayersRepository p_repository;
	private SessionsRepository s_repository;

	public AuthenticationController(PlayersRepository p_rep, SessionsRepository s_rep, HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		this.p_repository = p_rep;
		this.s_repository = s_rep;
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
		else{
			String[] name = {"description","average"};
			String[] value = {nick,"0"};
			writeBody(toJson(name, value));
			PlayerSession session = s_repository.createSession(nick);
			response.addCookie(new Cookie("session_id", session.session_id()));
		}
	}
	
	public void cookie_service() throws IOException {
		try{for (Cookie cookie : request.getCookies()) {
			PlayerSession session = s_repository.findSession(cookie.getValue());
			if (session != null) {
				String nick = session.player_id();
				String[] name = {"description","average"};
				String[] value = {nick,"0"};
				writeBody(toJson(name, value));
			}
			
		}
		}
		catch(NullPointerException exc){
		}
		
	}
}
