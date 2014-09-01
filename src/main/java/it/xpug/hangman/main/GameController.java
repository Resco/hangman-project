package it.xpug.hangman.main;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameController extends Controller{
		
	private PlayersRepository p_repository;
	private SessionsRepository s_repository;
	private GamesRepository g_repository;
	private MovesRepository m_repository;
	
	public GameController(PlayersRepository p_rep, SessionsRepository s_rep, MovesRepository m_rep,
	HttpServletRequest request, GamesRepository g_rep, HttpServletResponse response) {
		super(request, response);
		this.p_repository = p_rep;
		this.s_repository = s_rep;
		this.g_repository = g_rep;
		this.m_repository = m_rep;
	}

	public void new_game_service() throws IOException {
		for (Cookie cookie : request.getCookies()) {
			PlayerSession session = s_repository.findSession(cookie.getValue());
			if (session != null) {
				//aggiungi una partita con i dati utili
				Game game = g_repository.createGame(session.player_id());
				String[] name = {"game_id","code"};
				String[] value = {game.game_id(),game.code()};
				writeBody(toJson(name, value));
			}
	}
	}

	public void move_service() throws IOException {
		
		String code = g_repository.find_game_code(request.getParameter("game_id"));
		String answer = compareCodes(code, request.getParameter("sequence"));
		String player = g_repository.find_game_player(request.getParameter("game_id"));
		String move = request.getParameter("sequence");
		Move mov = m_repository.createMove(request.getParameter("game_id"), player, move, answer);
		g_repository.set_score(request.getParameter("game_id"));
		if(answer.equals("++++")){
			//prendo il player_id
			String player_id = g_repository.set_finished(request.getParameter("game_id"));
			//prendo lo score attuale
			int game_score = g_repository.find_game_score(request.getParameter("game_id"));
			//calcolo la nuova media:
			//prendendo il totale
			int total = g_repository.total_score(player_id);
			//aggiungendo lo score dell'ultima
			//total = total + game_score;
			//aggiorno la media
			p_repository.add_finished_game(player_id, total);
		}
		String[] name = {"move_id","move", "result"};
		String[] value = {mov.move_id() + "", mov.move(), mov.result()};
		writeBody(toJson(name, value));
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
