package com.lma.authentificator.model;

import org.springframework.util.StringUtils;

public class Register extends Login {

	private String firstName;

	private String lastName;

	private String webSiteName;

	private String email;

	private String langage;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWebSiteName() {
		return this.webSiteName;
	}

	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}

	public String getLangage() {
		return this.langage;
	}

	public void setLangage(String langage) {
		this.langage = langage;
	}

	@Override
	public boolean isNullField() {
		return StringUtils.isEmpty(this.firstName) || StringUtils.isEmpty(this.lastName) || StringUtils.isEmpty(super.getPseudo()) || StringUtils.isEmpty(this.email) || StringUtils.isEmpty(super.getPassword()) || StringUtils.isEmpty(this.webSiteName);
	}

}
