package it.xpug.hangman.main;

import java.security.MessageDigest;

public class Player {
	private String playerNick;
	private String password;

	public Player(String nickname, String password) {
		this.playerNick = nickname;
		this.password = password;
	}

	public String playerNick() {
		return playerNick;
	}

	public String password() {
		return password;
	}

	public String encryptedPassword() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return toHexString(digest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static String toHexString(byte [] bytes) {
		String symbols = "0123456789abcdef";
		String result = "";
		for (byte b : bytes) {
			int i = b & 0xFF;
			result += symbols.charAt(i / 16);
			result += symbols.charAt(i % 16);
		}
		return result;
	}
}
