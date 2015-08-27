package br.com.s2.mercadorias.exception;


public class ParametroJsonNaoEncontradoException extends S2Exception {
	private static final long serialVersionUID = 1L;

	public ParametroJsonNaoEncontradoException(String parametro) {
		super("Parametro Json ["+ parametro +"] não encontrado. Por favor, verifique se ele realmente foi enviado no corpo do Json!");
	}


}
