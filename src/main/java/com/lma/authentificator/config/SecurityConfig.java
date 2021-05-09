package com.lma.authentificator.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lma.authentificator.interceptor.JwtInterceptor;
import com.lma.authentificator.security.TokenManagement;
import com.lma.authentificator.service.UserService;

/**
 * Contains keys configurations.
 */
@Configuration
public class SecurityConfig {

	@Value("${jwt.unchecked.uri.list}")
	private String uriListString;

	/** The expiration delay. */
	@Value("${jwt.expiration.delay}")
	private int expirationDelay;

	/**
	 * Instanciate bean from application certificate.
	 *
	 * @return the public key
	 * @throws InvalidKeySpecException the invalid key spec exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	@Bean
	public PublicKey publicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {

		final InputStream inputStreamPublic = this.getClass().getClassLoader().getResourceAsStream("keys/public_key.pem");

		String publicKeyContent = new BufferedReader(
				new InputStreamReader(inputStreamPublic, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

		publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

		final KeyFactory kf = KeyFactory.getInstance("RSA");
		final X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));

		return kf.generatePublic(keySpecX509);

	}

	/**
	 * Instanciate bean from application key.
	 *
	 * @return the private key
	 * @throws Exception the exception
	 */
	@Bean
	public PrivateKey privateKey() throws Exception {

		final InputStream inputStreamPrivate = this.getClass().getClassLoader() .getResourceAsStream("keys/private_key_pkcs8.pem");

		String privateKeyContent = new BufferedReader(
				new InputStreamReader(inputStreamPrivate, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

		privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

		final KeyFactory kf = KeyFactory.getInstance("RSA");

		final PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));

		return kf.generatePrivate(keySpecPKCS8);
	}

	/**
	 * Token management.
	 *
	 * @param privateKey the private key
	 * @param publicKey the public key
	 * @return the token management
	 */
	@Bean
	public TokenManagement tokenManagement(@Autowired PrivateKey privateKey, @Autowired PublicKey publicKey) {
		return new TokenManagement(publicKey, privateKey, this.expirationDelay);
	}

	/**
	 * Jwt interceptor.
	 *
	 * @return the jwt interceptor
	 */
	@Bean
	public JwtInterceptor jwtInterceptor(@Autowired UserService userService, @Autowired PublicKey publicKey) {
		return new JwtInterceptor(publicKey, Arrays.asList(this.uriListString.split(",")));
	}

}