package com.lma.authentificator.model;

/**
 * Class Token.
 */
public class LoginInformations {

	private String pseudo;

	/** The token. */
	private String token;

	/** The langage. */
	private String langage;

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the langage.
	 *
	 * @return the langage
	 */
	public String getLangage() {
		return this.langage;
	}

	/**
	 * Sets the langage.
	 *
	 * @param langage the new langage
	 */
	public void setLangage(String langage) {
		this.langage = langage;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}



}
