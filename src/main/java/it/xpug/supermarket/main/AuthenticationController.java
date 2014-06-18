package it.xpug.supermarket.main;

import java.io.*;

import javax.servlet.http.*;

public class AuthenticationController extends Controller {

	private CashierRepository repository;
	private SessionsRepository sessions;

	public AuthenticationController(SessionsRepository sessions, CashierRepository cashiers, HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.sessions = sessions;
		this.repository = cashiers;
	}

	public void service() throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		Cashier cashier = new Cashier(Integer.valueOf(id), password);
		if (repository.cashierExists(cashier)) {
			String sessionId = sessions.createSession().id();
			writeBody(toJson("session_id", sessionId));
			response.addCookie(new Cookie("session_id", sessionId));
			response.setStatus(200);
		} else {
			response.setStatus(401);
			writeBody(toJson("description", "Bad id or password"));
		}
	}

}
