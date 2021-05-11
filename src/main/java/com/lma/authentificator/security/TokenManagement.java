package com.lma.authentificator.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.lma.authentificator.hibernate.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Class TokenManagement.
 */
public class TokenManagement {

	public TokenManagement(final PublicKey publicKey, final PrivateKey privateKey, final int expirationDelay) {
		super();
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.expirationDelay = expirationDelay;
	}


	private final PublicKey publicKey;

	private final PrivateKey privateKey;

	private final int expirationDelay;

	/**
	 * Generate token.
	 *
	 * @param privateKey the private key
	 * @return the string
	 */
	public final String generateToken(final UserEntity user) {

		final Map<String, Object> header = new HashMap<>();
		header.put("alg", "RS256");
		header.put("typ", "JWT");

		final Map<String, Object> claims = new HashMap<>();

		final Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
		calendar.add(Calendar.SECOND, this.expirationDelay);

		// put your information into claim
		claims.put("sub", user.getPseudo());
		claims.put("rol", user.getRoles());
		claims.put("iat", Calendar.getInstance().getTimeInMillis()/1000);
		claims.put("nbf", Calendar.getInstance().getTimeInMillis()/1000);
		claims.put("exp", calendar.getTimeInMillis()/1000);

		return Jwts.builder().setHeader(header).setClaims(claims).signWith(SignatureAlgorithm.RS256, this.privateKey).compact();
	}


	/**
	 * Verify token.
	 *
	 * @param token the token
	 * @param publicKey the public key
	 * @return the claims
	 */
	public final Claims verifyToken(final String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.publicKey).parseClaimsJws(token).getBody();

			System.out.println(claims.get("id"));
			System.out.println(claims.get("role"));

		} catch (final Exception e) {

			claims = null;
		}
		return claims;
	}

}
