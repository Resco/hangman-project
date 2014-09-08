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
				System.out.println(game.code());
				writeBody(toJson(name, value));
			}
	}
	}

	public void move_service() throws IOException {
		
		//prende il codice da indovinare, dato l'id partita
		String code = g_repository.find_game_code(request.getParameter("game_id"));
		//check sulla lunghezza
		if(request.getParameter("sequence").length()<4){
			writeBody(toJson("description", "short"));
			return;
		}
		//calcola il risultato
		String answer = compareCodes(code, request.getParameter("sequence"));
		//prende il nome del player, dato l'id partita
		String player = g_repository.find_game_player(request.getParameter("game_id"));
		//prende la stringa della mossa
		String move = request.getParameter("sequence");
		//crea un oggetto move
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
					player, avg + "", games+""};
			writeBody(toJson(name, value));
			return;
		}
		String[] name = {"move_id","move", "result",};
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
