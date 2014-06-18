package it.xpug.supermarket.main;

import java.util.*;

import javax.servlet.http.*;

public class WebRequest extends NullHttpServletRequest {

	private Map<String, String> parameters = new HashMap<String, String>();
	private Cookie cookie;

	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public Cookie[] getCookies() {
		if (null == cookie)
			return new Cookie[] {};
		return new Cookie[] { cookie };
	}

	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}

	public void addCookie(Cookie cookie) {
		this.cookie = cookie;
	}
}
