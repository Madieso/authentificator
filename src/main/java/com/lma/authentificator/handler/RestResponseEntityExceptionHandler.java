package com.lma.authentificator.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lma.authentificator.constantes.RestConstants;
import com.lma.authentificator.model.ResponseRest;
import com.lma.authentificator.model.exception.RestException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(value = { RestException.class })
	protected ResponseEntity<ResponseRest> handleConflict(RestException ex, WebRequest request) {
		this.LOGGER.error(ex.getMessage());
		return new ResponseEntity<>(new ResponseRest(ex.getCode()), ex.getStatus());
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ResponseRest> handleGlobalException(Exception ex, WebRequest request) {
		this.LOGGER.error(ex.getMessage());
		return new ResponseEntity<>(new ResponseRest(RestConstants.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}