package com.bank.account.model;

public class Authresponse {

	//private String username;
	private String token;

	public Authresponse(String token) {
		this.token = token;
	}

	public Authresponse() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
