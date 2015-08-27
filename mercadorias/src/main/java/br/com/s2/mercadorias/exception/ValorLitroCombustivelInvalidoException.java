package br.com.s2.mercadorias.exception;

public class ValorLitroCombustivelInvalidoException extends S2Exception {

	private static final long serialVersionUID = 1L;

	public ValorLitroCombustivelInvalidoException(Float valorLitro) {
		super("Valor do litro do combustivel [" + valorLitro +"] invalido. Valor deve ser maior que 0!");
	}

}
