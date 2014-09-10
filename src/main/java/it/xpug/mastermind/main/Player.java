package it.xpug.mastermind.main;

import java.security.MessageDigest;
import java.util.Random;

public class Player {
	private String player_id;
	private String mail;
	private String salt;
	private String password;

	public Player(String nickname, String mail, String password) {
		Random random = new Random();
		this.player_id = nickname;
		this.password = password;
		this.mail = mail;
		this.salt = "" + random.nextInt(99999999);
	}

	public String player_id() {
		return player_id;
	}

	public String password() {
		return password;
	}
	
	public String salt() {
		return salt;
	}
	
	public String mail() {
		return mail;
	}

	public String encryptedPassword() {
		try {
			String seed = "" + password + salt;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(seed.getBytes("UTF-8"));
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
