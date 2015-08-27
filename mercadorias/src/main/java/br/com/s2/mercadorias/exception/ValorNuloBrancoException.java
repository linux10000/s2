package br.com.s2.mercadorias.exception;

import br.com.s2.mercadorias.helper.Helper;

public class ValorNuloBrancoException extends S2Exception {

	private static final long serialVersionUID = 1L;

	public ValorNuloBrancoException(String campo) {
		super("Valor para a variavel [" + Helper.stringNulo(campo) + "] não pode ser nulo!" );
	}

}
