package br.com.s2.mercadorias.helper;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapParametrosMessageConverter extends AbstractHttpMessageConverter<Map<String, Object>> {

	public MapParametrosMessageConverter() {
		super(MediaType.ALL);
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return Map.class.equals(clazz);
	}

	@Override
	protected Map<String, Object> readInternal(
			Class<? extends Map<String, Object>> clazz,
			HttpInputMessage httpInputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> lista = om.readValue(httpInputMessage.getBody(), new TypeReference<Map<String, Object>>() {});
		return  lista;		
	}

	@Override
	protected void writeInternal(Map<String, Object> lista,
			HttpOutputMessage httpOutputMessage) throws IOException,
			HttpMessageNotWritableException {
		ObjectMapper om = new ObjectMapper();
		httpOutputMessage.getBody().write(om.writeValueAsBytes(lista));
	}

}
