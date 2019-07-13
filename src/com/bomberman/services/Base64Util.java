package com.bomberman.services;

import java.util.Base64;

public class Base64Util {

	public static String getPasswordBase64(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

}
