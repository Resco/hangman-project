package it.xpug.mastermind.main;

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
		//crea una nuova partita
		
		for (Cookie cookie : request.getCookies()) {
			PlayerSession session = s_repository.findSession(cookie.getValue());
			if (session != null) {
				Game game = g_repository.createGame(session.player_id());
				String[] name = {"game_id","code"};
				String[] value = {game.game_id(),game.code()};
				//usato per test
				System.out.println(game.code());
				writeBody(toJson(name, value));
			}
	}
	}

	public void move_service() throws IOException {
		//gestisce le mosse, rispondendo con il risultato e salvando in db
		//la mossa stessa
		
		String code = g_repository.find_game_code(request.getParameter("game_id"));
		if(request.getParameter("sequence").length()<4){
			writeBody(toJson("description", "short"));
			return;
		}
		String answer = compareCodes(code, request.getParameter("sequence"));
		String player = g_repository.find_game_player(request.getParameter("game_id"));
		String move = request.getParameter("sequence");
		Move mov = m_repository.createMove(request.getParameter("game_id"), player, move, answer);
		//setta il punteggio della partita a seguito della mossa
		g_repository.set_score(request.getParameter("game_id"));
		//se il risultato è una vittoria
		
		if(answer.equals("++++")){
			//indica come finita la partita in corso (data fine)
			g_repository.set_finished(request.getParameter("game_id"));
			//calcola il punteggio su tutte le partite
			int total = g_repository.total_score(player);
			//aumenta conto partite finite e aggiorna media
			p_repository.add_finished_game(player, total);
			//prendi media e numero partite
			float avg = p_repository.get_average(player);
			int games = p_repository.get_games(player);
			
			String[] name = {"move_id","move", "result",
					"description", "average" , "games"};
			String[] value = {mov.move_id() + "", mov.move(), mov.result(),
					player, String.format("%.2f", avg) + "", games+""};
			writeBody(toJson(name, value));
			return;
		}
		String[] name = {"move_id","move", "result",};
		String[] value = {mov.move_id() + "", mov.move(), mov.result()};
		writeBody(toJson(name, value));
	}

	private String compareCodes(String codeToGuess, String codeSubmitted) {
		//logica principale del gioco: data una stringa, calcola i + e i -
		
		String answer = "";
		Boolean toGuess[] = {false, false, false, false};
		Boolean submitted[] = {false, false, false, false};
		for(int i=0; i<4; i++){
			if(codeSubmitted.charAt(i)==codeToGuess.charAt(i)){
				submitted[i]=true;
				toGuess[i]=true;
				answer = answer + "+";
			}
		}
		
		//scorro tutte le lettere della mossa
		for(int j=0; j<4; j++){
			//se la lettera non è stata ancora assegnata
			if(submitted[j]==false){
				//scorro le lettere del codice
				for(int y=0;y<4;y++){
					//se la lettera non è ancora stata assegnata
					if(toGuess[y]==false){
						//metti - se c'è coincidenza e segna come controllate
						if(codeSubmitted.charAt(j)==codeToGuess.charAt(y)){
							submitted[j]=true;
							toGuess[y]=true;
							answer=answer + "-";
							break;
						}
					}
				}
			}
		}
		
		
		return answer;
		
	}

}
