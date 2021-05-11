package com.lma.authentificator.model;

import com.lma.authentificator.hibernate.entity.UserEntity;

/**
 * Class User: user of an application.
 */
public class PublicUser {

	public PublicUser() {}

	public PublicUser(final UserEntity entity) {
		this.email = entity.getEmail();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.langage = entity.getLangage();
	}

	private String email;

	private String firstName;

	private String lastName;

	private String langage;

	private String password;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getLangage() {
		return this.langage;
	}

	public void setLangage(final String langage) {
		this.langage = langage;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
