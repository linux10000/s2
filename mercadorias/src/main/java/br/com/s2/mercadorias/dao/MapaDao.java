package br.com.s2.mercadorias.dao;

import java.util.List;

import br.com.s2.mercadorias.pojo.Mapa;

public interface MapaDao extends GenericDao<Mapa> {

	/**
	 * lista todos os mapas com mapcdescri = nome
	 * @param nome = mapcdescri
	 * @return 
	 */
	public List<Mapa> obtemMapasPorNome(String nome);
}
