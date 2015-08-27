package br.com.s2.mercadorias.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.s2.mercadorias.helper.Helper;
import br.com.s2.mercadorias.pojo.json.RotaJson;

public class OrigemDestinoInvalidoException extends S2Exception {

	private static final long serialVersionUID = 1L;

	public OrigemDestinoInvalidoException(String mapa, RotaJson rota)
			throws JsonProcessingException {
		super(
				"Rota do mapa ["
						+ Helper.stringNulo(mapa)
						+ "]  com valor invalido para origem/destino. Apenas valores de 'A' a 'Z' sao permitidos. Rota ["
						+ new ObjectMapper().writeValueAsString(rota) + "].");
	}
	
	public OrigemDestinoInvalidoException(String mapa, String rota){
		super(
				"Rota do mapa ["
						+ Helper.stringNulo(mapa)
						+ "]  com valor invalido para origem/destino. Apenas valores de 'A' a 'Z' sao permitidos. Rota ["
						+ rota + "].");
	}

}
