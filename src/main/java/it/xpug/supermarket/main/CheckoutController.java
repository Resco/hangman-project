package it.xpug.supermarket.main;

import java.io.*;

import javax.servlet.http.*;

public class CheckoutController extends Controller {
	private CheckoutRepository repository;

	public CheckoutController(CheckoutRepository repository,
			HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.repository = repository;
	}

	public void service() throws IOException {
		if (request.getRequestURI().equals("/scan")) {
			doScan();
		} else if (request.getRequestURI().equals("/total")) {
			doTotal();
		} else {
			do404();
		}
	}

	private void doScan() throws IOException {
		try {
			SupermarketCheckout checkout = repository.findById(0);
			int price = checkout.scan(request.getParameter("code"));
			repository.save(checkout);
			writeBody(toJson("price", price));
		} catch (PriceList.PriceNotFound e) {
			response.setStatus(400);
			writeBody(toJson("description", "Price not found"));
		}
	}

	private void doTotal() throws IOException {
		SupermarketCheckout checkout = repository.findById(0);
		int total = checkout.total();
		writeBody(toJson("total", total));
	}


}
