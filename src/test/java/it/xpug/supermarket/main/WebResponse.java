package it.xpug.supermarket.main;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

public class WebResponse extends NullHttpServletResponse {

	private int status;
	private StringWriter writer = new StringWriter();
	private Map<String, Cookie> cookies = new HashMap<String, Cookie>();

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(writer);
	}

	@Override
	public void setStatus(int sc) {
		this.status = sc;
	}

	@Override
	public void addCookie(Cookie cookie) {
		this.cookies.put(cookie.getName(), cookie);
	}

	public int getStatus() {
		return status;
	}

	public String getBody() {
		return writer.toString();
	}

	public Cookie getCookie(String name) {
		return cookies.get(name);
	}

}
