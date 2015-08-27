package br.com.s2.mercadorias.exception;

import br.com.s2.mercadorias.helper.Helper;

public class MapaSemRotaException extends S2Exception {

	private static final long serialVersionUID = 1L;

	public MapaSemRotaException(String mapa) {
		super("Mapa ["+ Helper.stringNulo(mapa) +"] não possui rotas!!");
	}


}
