package br.com.s2.mercadorias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.s2.mercadorias.dao.MapaDao;
import br.com.s2.mercadorias.exception.MapaSemRotaException;
import br.com.s2.mercadorias.exception.OrigemDestinoInvalidoException;
import br.com.s2.mercadorias.exception.ValorNuloBrancoException;
import br.com.s2.mercadorias.helper.Helper;
import br.com.s2.mercadorias.pojo.Mapa;
import br.com.s2.mercadorias.pojo.json.MapaJson;
import br.com.s2.mercadorias.pojo.json.RotaJson;

@Service
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class MapaService extends GenericService<Mapa> {
	
	@Lazy(true)
	@Autowired
	private MapaDao mapaDao;
	@Lazy(true)
	@Autowired
	private RotaService rotaService;
	
	/**
	 * insere lista de mapas e de rotas que estavam em formato JSON
	 * 
	 * @param mapasJson
	 *            List<MapaJson> que estavam em formato JSON
	 * @throws ValorNuloBrancoException
	 *             se algum mapa da lista estiver com nome nulo ou branco
	 * @throws MapaSemRotaException
	 *             se um mapa nao possuir rotas
	 * @throws OrigemDestinoInvalidoException
	 *             se origem e/ou destino de qualquer rota na lista de qualquer
	 *             mapa nao estiver dentro do intervalo A-Z
	 * @throws JsonProcessingException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void inserirMapasJsonComRotas(List<MapaJson> mapasJson) throws ValorNuloBrancoException, MapaSemRotaException, OrigemDestinoInvalidoException, JsonProcessingException{
		for (MapaJson mapaJson : mapasJson) {
			Mapa mapa = MapaJson.mock(mapaJson);
			mapa = inserirMapasJsonComRotas_resolveMapa(mapa);
			
			if ( mapaJson.getRotas() == null || mapaJson.getRotas().size() == 0 )
				throw new MapaSemRotaException(mapaJson.getNome());
			
			for (RotaJson rotaJson : mapaJson.getRotas()) {
				rotaService.inserirRota(RotaJson.mock(rotaJson, mapa));
			}
		}
	}
	
	/**
	 * verifica se mapa existe. Se existir, o retorna. Se nao, insere um novo mapa e o retorna
	 * @param mapa mapa a ser verificado e inserido, caso nao existe
	 * @return mapa attached
	 */
	private Mapa inserirMapasJsonComRotas_resolveMapa(Mapa mapa) {
		Mapa m = Helper.resolvePrimeiroItemNulo(mapaDao.obtemMapasPorNome(mapa.getMapcdescri()));
		
		if ( m == null )
			return super.inserir(mapa);
		else
			return m;
	}
}
