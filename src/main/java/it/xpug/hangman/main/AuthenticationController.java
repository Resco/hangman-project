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
			writeBody(toJson("description", "nick_exist"));
		}
		else if(!p_repository.correctPassword(nick, pw)){
			writeBody(toJson("description", "pw"));
		}
		else{
			float average = p_repository.get_average(nick);
			int games = p_repository.get_games(nick);
			PlayerSession session = s_repository.createSession(nick);
			String[] name = {"description","average", "games", "session_id"};
			String[] value = {nick,average + "", games + "", session.session_id()};
			writeBody(toJson(name, value));
			response.addCookie(new Cookie("session_id", session.session_id()));
		}
	}
	
	public void cookie_service() throws IOException {
		try{for (Cookie cookie : request.getCookies()) {
			PlayerSession session = s_repository.findSession(cookie.getValue());
			if (session != null) {
				String nick = session.player_id();
				float average = p_repository.get_average(nick);
				int games = p_repository.get_games(nick);
				String[] name = {"description","average","games", "session_id"};
				String[] value = {nick,average +"", games + "", cookie.getValue() + ""};
				writeBody(toJson(name, value));
			}
			
		}
		}
		catch(NullPointerException exc){
		}
		
	}
	
	public void logout_service() throws IOException{
		s_repository.delete_session(request.getParameter("session_id"));
		writeBody(toJson("status", "ok"));
	}
}
