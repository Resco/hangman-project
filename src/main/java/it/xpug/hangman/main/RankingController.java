package it.xpug.hangman.main;

import it.xpug.generic.db.ListOfRows;

import java.io.IOException;
import java.sql.Timestamp;

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
		ListOfRows rows = p_repository.select_global_rank();
		String[] a1={"pos", "id","avg", "games"};
		String json="[";
		for (int i = 0; i < rows.size(); i++){
			String id= (String) rows.get(i).get("player_id");
			Float avg = (Float) rows.get(i).get("average");
			int games = (Integer) rows.get(i).get("num_games");
			String[] a2 = {""+(i+1), id, ""+avg, ""+games};
			if (i == 0) {
				json = json + toJson(a1,a2);
			} else {
				json += "," + toJson(a1,a2);
			}
		}
		json = json + "]";
		String toJsoned = toJson("players", json);
		toJsoned = toJsoned.replace("\"[", "[");
		toJsoned = toJsoned.replace("]\"", "]");
		System.out.println(toJsoned);
		writeBody(toJsoned);
	}
	
	public void service_player() throws IOException {
		String nickname = request.getParameter("nickname");
		ListOfRows rows = g_repository.select_player_games(nickname);
		String[] a1={"started", "finished","score",};
		String json="[";
		for (int i = 0; i < rows.size(); i++){
			Timestamp started= (Timestamp) rows.get(i).get("started");
			Timestamp finished = (Timestamp) rows.get(i).get("finished");
			int score = (Integer) rows.get(i).get("score");
			String[] a2 = {started+"", finished+"", ""+score};
			if (i == 0) {
				json = json + toJson(a1,a2);
			} else {
				json += "," + toJson(a1,a2);
			}
		}
		json = json + "]";
		String toJsoned = toJson("games", json);
		toJsoned = toJsoned.replace("\"[", "[");
		toJsoned = toJsoned.replace("]\"", "]");
		System.out.println(toJsoned);
		writeBody(toJsoned);
	}

}
