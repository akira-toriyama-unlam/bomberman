package com.bomberman.server;

public class LoginMessage {
	
	private AuthTypes authType;
	private String user;
	private String password;
	private boolean success;

	public LoginMessage(AuthTypes authType, String user, String password, boolean success) {
		this.authType = authType;
		this.user = user;
		this.password = password;
		this.success = success;
	}

	public AuthTypes getAuthType() {
		return authType;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
