package br.com.s2.mercadorias.exception;

import br.com.s2.mercadorias.helper.Helper;

public class RotaNaoDisponivelException extends S2Exception {

	private static final long serialVersionUID = 1L;

	public RotaNaoDisponivelException(String mapa, String origem, String destino) {
		super("Não foi possivel encontrar a rota para origem ["
				+ Helper.stringNulo(origem) + "] destino ["
				+ Helper.stringNulo(destino) + "] no mapa ["
				+ Helper.stringNulo(mapa)
				+ "]. Verifique se a rota foi cadastrada!");
	}

}
