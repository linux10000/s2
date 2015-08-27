package br.com.s2.mercadorias.exception;

public class ValorAutonomiaInvalidoException extends S2Exception {

	private static final long serialVersionUID = 1L;

	public ValorAutonomiaInvalidoException(Float autonomia) {
		super("Valor da autonomia [" + autonomia +"] invalido. Valor deve ser maior que 0!");
	}

}
