package it.xpug.supermarket.main;


import it.xpug.generic.db.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class CheckoutServlet extends HttpServlet {

	private DatabaseConfiguration configuration;
	private SessionsRepository sessions;

	public CheckoutServlet(DatabaseConfiguration configuration) {
		this.configuration = configuration;
		this.sessions = new SessionsRepository(new Random());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database database = new Database(configuration);
		CheckoutRepository repository = new CheckoutRepository(database);
		CashierRepository cashiers = new CashierRepository(database);

		AuthorizationFilter filter = new AuthorizationFilter(sessions, request, response);
		AuthenticationController authentication = new AuthenticationController(sessions, cashiers, request, response);
		CheckoutController checkoutController = new CheckoutController(repository, request, response);

		if (request.getRequestURI().equals("/authenticate")) {
			authentication.service();
			return;
		}

		if (!filter.canContinue())
			return;

		checkoutController.service();
	}
}
