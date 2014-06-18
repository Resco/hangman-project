package it.xpug.supermarket.main;

import java.io.*;

import javax.servlet.http.*;

public class AuthorizationFilter extends Controller {

	private SessionsRepository sessions;

	public AuthorizationFilter(SessionsRepository sessions, HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.sessions = sessions;
	}

	public boolean canContinue() throws IOException {
		for (Cookie cookie : request.getCookies()) {
			if (sessions.findSession(cookie.getValue()) != null) {
				return true;
			}
		}
		response.setStatus(401);
		writeBody(toJson("description", "Please go to /authenticate"));
		return false;
	}

}
