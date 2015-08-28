package br.com.s2.mercadorias.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.s2.mercadorias.dao.MapaDao;
import br.com.s2.mercadorias.dao.RotaDao;
import br.com.s2.mercadorias.exception.OrigemDestinoInvalidoException;
import br.com.s2.mercadorias.exception.RotaNaoDisponivelException;
import br.com.s2.mercadorias.exception.ValorAutonomiaInvalidoException;
import br.com.s2.mercadorias.exception.ValorLitroCombustivelInvalidoException;
import br.com.s2.mercadorias.exception.ValorNuloBrancoException;
import br.com.s2.mercadorias.helper.Helper;
import br.com.s2.mercadorias.pojo.Mapa;
import br.com.s2.mercadorias.pojo.Rota;

@Service
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class RotaService extends GenericService<Rota> {

	@Lazy(true)
	@Autowired
	private RotaDao rotaDao;
	@Lazy(true)
	@Autowired
	private MapaDao mapaDao;
	
	/**
	 * verifica se rota jah existe para mapa indicado. Se existir, tem seu valor
	 * de distancia substituido. Se nao existir, eh inserida.
	 * 
	 * @param rota
	 *            rota a ser inserida
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void inserirRota(Rota rota) {
		Rota aux = 
				Helper.resolvePrimeiroItemNulo(
						rotaDao.obtemRotaPorOrigemDestino(
								rota.getRotnorigem(), 
								rota.getRotndestino(), 
								rota.getMapa().getMapcdescri()
								)
							);
		
		if ( aux == null )
			super.inserir(rota);
		else{
			aux.setRotcdistancia(rota.getRotcdistancia());
			super.alterar(aux);
		}
	}
	
	/**
	 * 
	 * @param mapcdescri
	 *            nome do mapa
	 * @param origem
	 *            nome da origem
	 * @param destino
	 *            nome do destino
	 * @param autonomia
	 *            deve ser > 0
	 * @param valorLitro
	 *            deve ser > 0
	 * @return um map com 3 chave. "rota" = List<Rota> das rotas encontradas.
	 *         "distancia" = soma das distancias das rotas encontradas "custo" =
	 *         (distancia / automonia[2 casas decimais]) * valor do litro do
	 *         combustivel
	 * @throws OrigemDestinoInvalidoException
	 *             origem ou destino nao estiver no intervalo A-Z
	 * @throws RotaNaoDisponivelException
	 *             nenhuma possivel rota encontrada para origem e destino
	 * @throws ValorAutonomiaInvalidoException
	 *             se <= 0
	 * @throws ValorLitroCombustivelInvalidoException
	 *             se <= 0
	 * @throws ValorNuloBrancoException
	 *             se mapcdescri nulo ou branco
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public Map<String, Object> obtemCaminhoMenorCusto(String mapcdescri, String origem, String destino, Float autonomia, Float valorLitro) throws OrigemDestinoInvalidoException, RotaNaoDisponivelException, ValorAutonomiaInvalidoException, ValorLitroCombustivelInvalidoException, ValorNuloBrancoException{
		origem = Helper.stringNulo(origem).toUpperCase();
		destino = Helper.stringNulo(destino).toUpperCase();
		
		if ( !Helper.stringNulo(origem).matches("^[A-Z]$") 
				|| !Helper.stringNulo(destino).matches("^[A-Z]$")
				)
			throw new OrigemDestinoInvalidoException(mapcdescri, "Origem: " +origem + " - Destino: " + destino);
		
		if ( autonomia == null || autonomia <= 0 )
			throw new ValorAutonomiaInvalidoException((autonomia == null ? 0 : autonomia));
		
		if ( valorLitro == null || valorLitro <= 0 )
			throw new ValorLitroCombustivelInvalidoException((valorLitro == null ? 0 : valorLitro));
		
		if ( mapcdescri == null || mapcdescri.trim().isEmpty() )
			throw new ValorNuloBrancoException("mapa");
		
		Mapa mapa = Helper.resolvePrimeiroItemNulo(mapaDao.obtemMapasPorNome(mapcdescri));


		Map<String, Object> aux = obtemMenorDistancia(null, mapa.getMapnid(), origem.charAt(0), destino.charAt(0));
		Long distancia = (Long) aux.get("distancia");
		List<Rota> listaRotas = ((List<Rota>) aux.get("listaRotas"));
		if ( listaRotas == null || listaRotas.size() == 0 )
			throw new RotaNaoDisponivelException(mapcdescri, origem, destino);
		
		String caminho = obtemRotaHorizontal(listaRotas);
		float custo = calculaCustoRota(autonomia.doubleValue(), valorLitro.doubleValue(), distancia);
		
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("rota", caminho);
		r.put("custo", custo);
		r.put("distancia", distancia);
		
		return r;
	}
	
	/**
	 * obtem rotas em formato horizontal (string) dada uma lista de rotas
	 * @param lista
	 * @return conjunto de pontos de origem e destino de uma lista de rotas em string
	 * @throws ValorNuloBrancoException se 'lista' for nulo ou vazia
	 */
	public String obtemRotaHorizontal(List<Rota> lista) throws ValorNuloBrancoException{
		if ( lista == null || lista.isEmpty() )
			throw new ValorNuloBrancoException("parametro 'lista' do metodo 'obtemRotaHorizontal'");
		
		String r = "";
		for (Rota rota : lista) {
			if ( lista.get(0) != rota)
				r = r + " " + String.valueOf(Character.toChars(rota.getRotndestino()));
			else
				r = String.valueOf(Character.toChars(rota.getRotnorigem())) + " " + String.valueOf(Character.toChars(rota.getRotndestino()));
		}
		return r;
	}
	
	/**
	 * calcula custo
	 * @param autonomia > 0
	 * @param valorLitro > 0
	 * @param distancia > 0
	 * @return (distancia / autonomia) * valorLitro
	 * @throws ValorAutonomiaInvalidoException se <= 0
	 * @throws ValorLitroCombustivelInvalidoException se <= 0
	 * @throws ValorNuloBrancoException se distancia <= 0
	 */
	public float calculaCustoRota(double autonomia, double valorLitro, long distancia) throws ValorAutonomiaInvalidoException, ValorLitroCombustivelInvalidoException, ValorNuloBrancoException{
		if ( autonomia <= 0 )
			throw new ValorAutonomiaInvalidoException((float)autonomia);
		if ( valorLitro <= 0 )
			throw new ValorLitroCombustivelInvalidoException((float) valorLitro);
		if ( distancia <= 0 )
			throw new ValorNuloBrancoException(" 'distancia' no metodo 'calculaCustoRota'");
		
		BigDecimal valor = new BigDecimal(distancia).divide(BigDecimal.valueOf(autonomia), 2, BigDecimal.ROUND_HALF_UP);
		valor = valor.multiply(BigDecimal.valueOf( valorLitro));
		return valor.floatValue();
	}
	
	/**
	 * Obtem rota com menor distancia (e nao menor rota) de forma recursiva
	 * 
	 * @param params
	 *            sempre passar null
	 * @param mapnid
	 *            id do mapa
	 * @param origem
	 *            ascii da origem
	 * @param destino
	 *            ascii do destino
	 * @return retorna um map com duas chaves, "listaRotas" (List<Rota> com as
	 *         rotas selecionadas) e "distancia" (soma da distancia das rotas
	 *         selecionadas)
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> obtemMenorDistancia(Map<String, Object> params, BigInteger mapnid, int origem, int destino){
		boolean primeiroNivel = false;
		if ( params == null ){
			primeiroNivel = true;
			List<Rota> listaRotas = new ArrayList<Rota>();
			List<Rota> listaCorrente = new ArrayList<Rota>();
			params = new HashMap<String, Object>();
			params.put("distancia", (long) 0);
			params.put("distanciaCorrente", (long) 0);
			params.put("listaCorrente", listaCorrente);
			params.put("listaRotas", listaRotas);
		}
			
		int tamanhoPagina = 10;
		int skip = 0;
		List<Rota> rotasPossiveis = rotaDao.obtemRotaPorOrigem(origem, mapnid, tamanhoPagina, skip);
		if ( rotasPossiveis == null || rotasPossiveis.size() == 0 )
			return params;

		do {
			for (Rota rota : rotasPossiveis) {
				params.put("distanciaCorrente", ((long) params.get("distanciaCorrente")) + rota.getRotcdistancia());
				((List<Rota>) params.get("listaCorrente")).add(rota);
				if ( rota.getRotndestino() == destino ){
					if ( ((long) params.get("distanciaCorrente")) < ((long) params.get("distancia")) || ((long) params.get("distancia")) == 0  ){
						params.put("distancia", (long) params.get("distanciaCorrente"));
						((List<Rota>) params.get("listaRotas")).clear();
						((List<Rota>) params.get("listaRotas")).addAll(((List<Rota>) params.get("listaCorrente")));
					}
					
					params.put("distanciaCorrente", ((long) params.get("distanciaCorrente")) - rota.getRotcdistancia());
					((List<Rota>) params.get("listaCorrente")).remove(rota);
	
					return params;
				}
				else {
					if ( rota.getRotndestino() < destino )
						obtemMenorDistancia(params, mapnid, rota.getRotndestino(), destino);
					
					params.put("distanciaCorrente", ((long) params.get("distanciaCorrente")) - rota.getRotcdistancia());
					((List<Rota>) params.get("listaCorrente")).remove(rota);
				}
				
			}
			
			skip = skip + 10;
			rotasPossiveis = rotaDao.obtemRotaPorOrigem(origem, mapnid, tamanhoPagina, skip);
		} while ( rotasPossiveis != null && rotasPossiveis.size() > 0);
		
		if ( primeiroNivel ){
			params.remove("distanciaCorrente");
			params.remove("listaCorrente");
		}
		return params;
	}
}
