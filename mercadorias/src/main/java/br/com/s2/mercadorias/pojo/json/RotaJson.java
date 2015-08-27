package br.com.s2.mercadorias.pojo.json;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.s2.mercadorias.exception.OrigemDestinoInvalidoException;
import br.com.s2.mercadorias.helper.Helper;
import br.com.s2.mercadorias.pojo.Mapa;
import br.com.s2.mercadorias.pojo.Rota;

public class RotaJson {

	private String origem;
	private String destino;
	private Integer distancia;

	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Integer getDistancia() {
		return distancia;
	}
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	public RotaJson() {
	}
	public RotaJson(String origem, String destino, Integer distancia) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
	}
	
	public static Rota mock(RotaJson json, Mapa mapa) throws OrigemDestinoInvalidoException, JsonProcessingException{
		if ( !Helper.stringNulo(json.origem).toUpperCase().matches("^[A-Z]$") 
				|| !Helper.stringNulo(json.destino).toUpperCase().matches("^[A-Z]$")
				)
			throw new OrigemDestinoInvalidoException(mapa.getMapcdescri(), json);
		
		Rota r = new Rota();
		r.setMapa(mapa);
		r.setRotcdistancia(json.getDistancia());
		
		int ascii = json.getOrigem().toUpperCase().charAt(0);
		r.setRotnorigem(ascii);
		ascii = json.getDestino().toUpperCase().charAt(0);
		r.setRotndestino(ascii);
		
		return r;
	}
}
