package com.lma.authentificator.model;

/**
 * Class User: user of an application.
 */
public class User {


	/** The id. */
	private Long id;

	/** The name. */
	private String name;

	/** The email. */
	private String email;

	/** The password. */
	private String password;

	/** The emailconfirm. */
	private Boolean emailconfirm;

	/** The uuid. */
	private String uuid;

	private String roles;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the emailconfirm.
	 *
	 * @return the emailconfirm
	 */
	public Boolean getEmailconfirm() {
		return this.emailconfirm;
	}

	/**
	 * Sets the emailconfirm.
	 *
	 * @param emailconfirm the new emailconfirm
	 */
	public void setEmailconfirm(Boolean emailconfirm) {
		this.emailconfirm = emailconfirm;
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return this.uuid;
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
