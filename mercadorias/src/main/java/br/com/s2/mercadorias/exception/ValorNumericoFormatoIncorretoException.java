package br.com.s2.mercadorias.exception;

import br.com.s2.mercadorias.helper.Helper;

public class ValorNumericoFormatoIncorretoException extends S2Exception {

	private static final long serialVersionUID = 1L;

	public ValorNumericoFormatoIncorretoException(String campo, String valor) {
		super("Valor numerico ["+ Helper.stringNulo(valor)+"] incorreto no campo ["+ Helper.stringNulo(campo) +"]");
	}
}
