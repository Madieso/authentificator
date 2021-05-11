package com.lma.authentificator.controller;

import java.net.URI;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lma.authentificator.constantes.RestConstants;
import com.lma.authentificator.hibernate.entity.UserEntity;
import com.lma.authentificator.model.Login;
import com.lma.authentificator.model.LoginInformations;
import com.lma.authentificator.model.PublicUser;
import com.lma.authentificator.model.Register;
import com.lma.authentificator.model.ResponseRest;
import com.lma.authentificator.security.TokenManagement;
import com.lma.authentificator.service.UserService;

/**
 * AuthentificatorController.
 */
@RestController
@RequestMapping("/account")
public class AuthentificatorController {

	/** The logger. */
	Logger LOGGER = LoggerFactory.getLogger(AuthentificatorController.class);

	/** The front end url. */
	@Value("${frontend.url}")
	private String frontEndUrl;

	/** The email. */
	@Value("${mail.sender.email}")
	private String email;

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The email sender. */
	@Autowired
	private JavaMailSender emailSender;

	/** The private key. */
	@Autowired
	private PrivateKey privateKey;

	/** The public key. */
	@Autowired
	private PublicKey publicKey;

	/** The token management. */
	@Autowired
	private TokenManagement tokenManagement;

	/**
	 * Login.
	 *
	 * @param login the login
	 * @return the response entity
	 * @throws Exception the exception
	 * @status 403: bad password
	 * @status 406: bad fields body
	 * @status 409: user not exists
	 * @status 412: account not confirm
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody final Login login) throws Exception {

		if(login.isNullField()) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.ALL_FIELDS_MUST_BE_FILLED), HttpStatus.NOT_ACCEPTABLE);
		}

		final UserEntity user = this.userService.findByPseudo(login.getPseudo());

		if(user == null) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.PSEUDO_NOT_EXIST), HttpStatus.NOT_ACCEPTABLE);
		}

		if(!user.getEmailconfirm()) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.ACCOUNT_NOT_CONFIRM), HttpStatus.FORBIDDEN);
		}

		Cipher rsa;
		rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.DECRYPT_MODE, this.publicKey);
		final byte[] utf8 = rsa.doFinal(user.getPassword());
		final String password = new String(utf8, "UTF8");

		if(!login.getPassword().equals(password)) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.BAD_PASSWORD), HttpStatus.FORBIDDEN);
		}

		final LoginInformations token = new LoginInformations();
		token.setToken(this.tokenManagement.generateToken(user));
		token.setLangage(user.getLangage());
		token.setPseudo(user.getPseudo());

		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	/**
	 * Register.
	 *
	 * @param register the register
	 * @return the response entity
	 * @throws Exception the exception
	 * @status 403: pseudo already exists
	 * @status 406: bad fields body
	 * @status 409: user already exists
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody final Register register) throws Exception {

		if(register.isNullField()) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.ALL_FIELDS_MUST_BE_FILLED), HttpStatus.NOT_ACCEPTABLE);
		}

		final UserEntity user = new UserEntity(register, this.privateKey);

		if(this.userService.findByEmail(user.getEmail()) != null) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.EMAIL_ALREADY_EXIST), HttpStatus.CONFLICT);
		}

		if(this.userService.findByPseudo(user.getPseudo()) != null) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.PSEUDO_ALREADY_EXIST), HttpStatus.CONFLICT);
		}

		this.userService.save(user);

		final SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(this.email);
		message.setTo(user.getEmail());
		message.setSubject(String.format("Confirm your inscription to %s", register.getWebSiteName()));
		message.setText(String.format("Please confirm your inscription here %s/confirmRegister?uuid=%s", this.frontEndUrl, user.getUuid()));
		this.emailSender.send(message);

		return ResponseEntity.created(new URI(user.getEmail())).build();
	}

	/**
	 * Confirm register.
	 *
	 * @param uuid the uuid
	 * @return the response entity
	 * @throws Exception the exception
	 * @status 406: user doesn't exists
	 */
	@PostMapping("/confirmRegister")
	public ResponseEntity<?> confirmRegister(@RequestParam(required = false, value="uuid") final String uuid) throws Exception {

		final UserEntity user = this.userService.findByUuid(uuid);

		if(user == null) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.ACCOUND_NOT_EXIST), HttpStatus.NOT_ACCEPTABLE);
		}

		if(user.getEmailconfirm()) {
			return new ResponseEntity<>(new ResponseRest(RestConstants.ACCOUNT_ALREADY_CONFIRM), HttpStatus.CONFLICT);
		}

		user.setEmailconfirm(true);
		this.userService.save(user);

		final LoginInformations token = new LoginInformations();
		token.setToken(this.tokenManagement.generateToken(user));
		token.setLangage(user.getLangage());
		token.setPseudo(user.getPseudo());

		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	/**
	 * Change langage.
	 *
	 * @param request the request
	 * @param langage the langage
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/langage")
	public ResponseEntity<?> changeLangage(final HttpServletRequest request, @RequestParam(required = false, value="langage") final String langage) throws Exception {

		final String username = (String) request.getAttribute("username");

		final UserEntity user = this.userService.findByPseudo(username);
		user.setLangage(langage);

		this.userService.save(user);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Register.
	 *
	 * @param register the register
	 * @return the response entity
	 * @throws Exception the exception
	 * @status 403: pseudo already exists
	 * @status 406: bad fields body
	 * @status 409: user already exists
	 */
	@PutMapping
	public ResponseEntity<?> updateAccount(final HttpServletRequest request, @RequestBody final PublicUser publicUser) throws Exception {

		final String username = (String) request.getAttribute("username");

		final UserEntity entity = this.userService.findByPseudo(username);

		if(publicUser.getEmail() != null) {
			entity.setEmail(publicUser.getEmail());
		}

		if(publicUser.getFirstName() != null) {
			entity.setFirstName(publicUser.getFirstName());
		}

		if(publicUser.getLastName() != null) {
			entity.setLastName(publicUser.getLastName());
		}

		if(publicUser.getLangage() != null) {
			entity.setLangage(publicUser.getLangage());
		}

		if(publicUser.getPassword() != null) {
			// Encrypt password
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, this.privateKey);
			entity.setPassword(cipher.doFinal(publicUser.getPassword().getBytes()));
		}

		this.userService.save(entity);

		return new ResponseEntity<>(publicUser, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAccountInfo(final HttpServletRequest request) throws Exception {

		final String username = (String) request.getAttribute("username");
		return new ResponseEntity<>(new PublicUser(this.userService.findByPseudo(username)), HttpStatus.OK);
	}

}
