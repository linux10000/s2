package br.com.s2.mercadorias.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.s2.mercadorias.helper.Helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandlerController {

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public String manipulaException(Exception exception, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException{
		System.out.println(Helper.mostraException(exception));
		if ( request.getRequestURI().contains("/sistema/rest/") ){
			Map<String, String> r = new HashMap<String, String>();
			r.put("erro", Helper.mostraException(exception));
			return new ObjectMapper().writeValueAsString(r);
		}
		else{
			return Helper.mostraException(exception);
		}
	}
}
