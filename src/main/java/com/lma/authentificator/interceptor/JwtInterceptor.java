package com.lma.authentificator.interceptor;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lma.authentificator.constantes.RestConstants;
import com.lma.authentificator.model.exception.RestException;

/**
 * The Class JwtInterceptor.
 */
public class JwtInterceptor implements HandlerInterceptor {

	Logger LOGGER = LoggerFactory.getLogger(JwtInterceptor.class);

	/** The public key. */
	private final PublicKey publicKey;

	private final List<String> uncheckedUriList;

	/**
	 * Instantiates a new jwt interceptor.
	 *
	 * @param userDao the user dao
	 */
	public JwtInterceptor(final PublicKey publicKey, final List<String> uncheckedUriList) {
		super();
		this.publicKey = publicKey;
		this.uncheckedUriList = uncheckedUriList;
	}

	/**
	 * Pre handle.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param httpServletResponse the http servlet response
	 * @param object the object
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

		if(this.uncheckedUriList.contains(httpServletRequest.getRequestURI())) {
			return true;
		}

		final String jwtToken = httpServletRequest.getHeader("authorization");

		if(StringUtils.isEmpty(jwtToken)) {
			throw new RestException(HttpStatus.UNAUTHORIZED, RestConstants.UNAUTHORIZED);
		}

		DecodedJWT token = null;
		try {
			token = JWT.decode(jwtToken);

			final Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) this.publicKey, null);
			final JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(token);
			httpServletRequest.setAttribute("username", token.getClaim("sub").asString());

		} catch(final Exception ex) {
			throw new RestException(HttpStatus.UNAUTHORIZED, RestConstants.UNAUTHORIZED);
		}

		return true;
	}

	/**
	 * Post handle.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param httpServletResponse the http servlet response
	 * @param o the o
	 * @param modelAndView the model and view
	 * @throws Exception the exception
	 */
	@Override
	public void postHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Object o, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * After completion.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param httpServletResponse the http servlet response
	 * @param o the o
	 * @param e the e
	 * @throws Exception the exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}

}

