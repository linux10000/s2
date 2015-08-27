package br.com.s2.mercadorias.helper;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import br.com.s2.mercadorias.pojo.json.GenericJson;
import br.com.s2.mercadorias.pojo.json.MapaJson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericJsonMessageConverter extends AbstractHttpMessageConverter<GenericJson> {

	public GenericJsonMessageConverter() {
		super(MediaType.ALL);
	}
	
	@Override
	protected GenericJson readInternal(Class<? extends GenericJson> pojo,
			HttpInputMessage httpInputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		ObjectMapper om = new ObjectMapper();
		List<MapaJson> lista = om.readValue(httpInputMessage.getBody(), new TypeReference<List<MapaJson>>() {});
		return (GenericJson) lista;		
	}

	@Override
	protected boolean supports(Class<?> arg0) {
		return GenericJson.class.equals(arg0);
	}

	@Override
	protected void writeInternal(GenericJson pojo, HttpOutputMessage httpOutputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		ObjectMapper om = new ObjectMapper();
		httpOutputMessage.getBody().write(om.writeValueAsBytes(pojo));
	}

}
