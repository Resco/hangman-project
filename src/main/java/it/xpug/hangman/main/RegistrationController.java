package it.xpug.hangman.main;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends Controller {
	
	private PlayersRepository p_repository;

	public RegistrationController(PlayersRepository p_rep, HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		this.p_repository = p_rep;
	}

	public void service() throws IOException{
		String nick = request.getParameter("nickname");
		String pw = request.getParameter("password");
		String re_pw = request.getParameter("re_password");
		String mail = request.getParameter("mail");
		if(pw.length()<8){
			writeBody(toJson("description", "We need an 8 words password"));
		}
		else if (!pw.equals(re_pw)){
			writeBody(toJson("description", "Digited password aren't equal"));
		}
		else if (mail.indexOf('@')==-1){
			writeBody(toJson("description", "Your mail doesn't have the '@'"));
		}
		else if (p_repository.nicknameExists(nick)){
			writeBody(toJson("description", "Please select another nickname"));
		}
		else{
			Player player = new Player(nick, mail, pw);
			p_repository.add(player);
			writeBody(toJson("description", "Nice job! You just registered to Hangman"));
		}
		
	}
}
