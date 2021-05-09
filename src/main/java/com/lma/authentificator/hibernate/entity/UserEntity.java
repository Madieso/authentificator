package com.lma.authentificator.hibernate.entity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.lma.authentificator.model.Register;
import com.sun.istack.NotNull;

/**
 * Represents an User for this web application.
 */
@Entity(name = "authentificator_user")
public class UserEntity {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authentificator_user_generator")
	@SequenceGenerator(name="authentificator_user_generator", sequenceName = "authentificator_user_seq", allocationSize=1)
	@Column(name = "authentificator_user_id")
	private long id;

	/** The email. */
	@NotNull
	@Column(name = "authentificator_email")
	private String email;

	/** The name. */
	@NotNull
	@Column(name = "authentificator_first_name")
	private String firstName;

	/** The last name. */
	@NotNull
	@Column(name = "authentificator_last_name")
	private String lastName;

	/** The pseudo. */
	@NotNull
	@Column(name = "authentificator_pseudo")
	private String pseudo;

	/** The password. */
	@NotNull
	@Column(name = "authentificator_password")
	private byte[] password;

	/** The emailconfirm. */
	@NotNull
	@Column(name = "authentificator_email_confirmation")
	private Boolean emailconfirm;

	/** The uuid. */
	@NotNull
	@Column(name = "authentificator_uuid")
	private String uuid;

	/** The langage. */
	@NotNull
	@Column(name = "authentificator_lang")
	private String langage;

	/** The roles. */
	@NotNull
	@Column(name = "authentificator_roles")
	private String roles;

	// ------------------------
	// PUBLIC METHODS
	// ------------------------

	/**
	 * Instantiates a new user DTO.
	 */
	public UserEntity() {
		this.roles = "[]";
	}

	/**
	 * Instantiates a new user DTO.
	 *
	 * @param id the id
	 */
	public UserEntity(long id) {
		this.id = id;
		this.roles = "[]";
	}

	/**
	 * Instantiates a new user DTO.
	 *
	 * @param register register info
	 * @param privateKey the private key
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws NoSuchPaddingException the no such padding exception
	 * @throws InvalidKeyException the invalid key exception
	 * @throws IllegalBlockSizeException the illegal block size exception
	 * @throws BadPaddingException the bad padding exception
	 */
	public UserEntity(Register register, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		this.firstName = register.getFirstName();
		this.lastName = register.getLastName();
		this.pseudo = register.getPseudo();
		this.email = register.getEmail();
		this.emailconfirm = false;
		this.langage = register.getLangage();
		this.roles = "[]";

		// Encrypt password
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		this.password = cipher.doFinal(register.getPassword().getBytes());
		this.uuid = UUID.randomUUID().toString();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param value the new id
	 */
	public void setId(long value) {
		this.id = value;
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
	 * @param value the new email
	 */
	public void setEmail(String value) {
		this.email = value;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public byte[] getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(byte[] password) {
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the pseudo.
	 *
	 * @return the pseudo
	 */
	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * Sets the pseudo.
	 *
	 * @param pseudo the new pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
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

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
