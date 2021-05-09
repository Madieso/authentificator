package com.lma.authentificator.model;

import org.springframework.util.StringUtils;

public class Login {

	private String password;

	private String pseudo;

	public String getPseudo() {
		return this.pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isNullField() {
		return StringUtils.isEmpty(this.pseudo) || StringUtils.isEmpty(this.password);
	}

}
