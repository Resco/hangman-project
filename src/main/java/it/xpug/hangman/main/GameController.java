package it.xpug.hangman.main;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameController extends Controller{
		
	private PlayersRepository p_repository;
	private SessionsRepository s_repository;
	private GamesRepository g_repository;
	
	public GameController(PlayersRepository p_rep, SessionsRepository s_rep, HttpServletRequest request,
			GamesRepository g_rep, HttpServletResponse response) {
		super(request, response);
		this.p_repository = p_rep;
		this.s_repository = s_rep;
		this.g_repository = g_rep;
	}

	public void new_game_service() throws IOException {
		for (Cookie cookie : request.getCookies()) {
			PlayerSession session = s_repository.findSession(cookie.getValue());
			if (session != null) {
				//aggiungi una partita con i dati utili
				Game game = g_repository.createGame(session.id_player());
				String[] name = {"game_id","code"};
				String[] value = {game.id_game(),game.secret_code()};
				writeBody(toJson(name, value));
			}
	}
	}

	public void move_service() throws IOException {
		
		String code = g_repository.find_game_code(request.getParameter("game_id"));
		String answer = compareCodes(code, request.getParameter("sequence"));
		writeBody(toJson("answer", answer));
	}

	private String compareCodes(String codeToGuess, String codeSubmitted) {
		String answer = "";
		Boolean checked[] = {false, false, false, false};
		for(int i=0; i<4; i++){
			if(codeSubmitted.charAt(i)==codeToGuess.charAt(i)){
				checked[i]=true;
				answer = answer + "+";
			}
		}
		for (int j=0; j<4; j++){
			if(checked[j]==false){
				for(int k=0; k<4; k++){
					if(checked[k]==false){
						if(codeSubmitted.charAt(j)==codeToGuess.charAt(k)){
							checked[k]=true;
							answer = answer + "-";
							break;
						}
					}
				}
			}
		}
		return answer;
		
	}

}
