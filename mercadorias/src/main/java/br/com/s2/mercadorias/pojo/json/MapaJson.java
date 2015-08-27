package br.com.s2.mercadorias.pojo.json;

import java.util.List;

import br.com.s2.mercadorias.exception.ValorNuloBrancoException;
import br.com.s2.mercadorias.pojo.Mapa;

public class MapaJson {

	private String nome;
	private List<RotaJson> rotas;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<RotaJson> getRotas() {
		return rotas;
	}
	public void setRotas(List<RotaJson> rotas) {
		this.rotas = rotas;
	}
	public MapaJson() {
	}
	public MapaJson(String nome, List<RotaJson> rotas) {
		this.nome = nome;
		this.rotas = rotas;
	}
	
	public static Mapa mock(MapaJson json) throws ValorNuloBrancoException{
		if ( json.getNome() == null || json.getNome().trim().isEmpty() )
			throw new ValorNuloBrancoException("nome do mapa");
		
		Mapa r = new Mapa();
		r.setMapcdescri(json.getNome());
		
		return r;
	}
}
