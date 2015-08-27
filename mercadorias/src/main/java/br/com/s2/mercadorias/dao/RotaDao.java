package br.com.s2.mercadorias.dao;

import java.math.BigInteger;
import java.util.List;

import br.com.s2.mercadorias.pojo.Rota;

public interface RotaDao extends GenericDao<Rota> {

	/**
	 * lista todas as rotas com rotcorigem = origem, rotcdestino = destino, mapcdescri = mapa
	 * @param origem = rotcorigem
	 * @param destino = rotcdestino
	 * @param mapa = mapcdescri
	 * @return
	 */
	public List<Rota> obtemRotaPorOrigemDestino(Integer origem, Integer destino, String mapa);
	/**
	 * lista todas as rotas com rotcorigem = origem, mapcdescri = mapa
	 * @param origem = rotcorigem
	 * @param mapa = mapcdescri
	 * @param tamanhoPagina maximo de registros listados em cada chamado
	 * @param skip numero de registros a serem "pulados" da lista original
	 * @return
	 */
	public List<Rota> obtemRotaPorOrigem(Integer origem, BigInteger mapa, int tamanhoPagina, int skip);
}
