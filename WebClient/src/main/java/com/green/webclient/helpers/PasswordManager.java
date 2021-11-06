package com.green.webclient.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordManager {

	public static String getBCrypPassword(String rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode(rawPassword);
		
		System.out.println("Encoded Password: " + encoded);
		
		return encoded; 
	}
}
