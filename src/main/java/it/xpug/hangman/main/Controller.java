package it.xpug.hangman.main;

import static java.lang.String.*;

import java.io.*;

import javax.servlet.http.*;

public class Controller {
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public Controller(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		response.setContentType("application/json");
	}

	protected void do404() throws IOException {
		response.setStatus(404);
		writeBody(toJson("description", "Not Found"));
	}

	protected String toJson(String name, String value) {
		return format("{ \"%s\": \"%s\" }", name, value);
	}

	protected String toJson(String name, int value) {
		return format("{ \"%s\": %s }", name, value);
	}
	
	protected String toJson(String[] name, String[] value) {
		String json="";
		for (int i=0; i<name.length; ++i){
			if (i == 0) {
				json+= format("\"%s\": \"%s\" ", name[i], value[i]);
			} else {
				json+= "," + format("\"%s\": \"%s\" ", name[i], value[i]);
			}
			
		}
		return "{" +json+ "}";
	}

	protected void writeBody(String body) throws IOException {
		response.getWriter().write(body);
	}

}
