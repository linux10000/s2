package br.com.s2.mercadorias.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.s2.mercadorias.exception.MapaSemRotaException;
import br.com.s2.mercadorias.exception.OrigemDestinoInvalidoException;
import br.com.s2.mercadorias.exception.ParametroJsonNaoEncontradoException;
import br.com.s2.mercadorias.exception.RotaNaoDisponivelException;
import br.com.s2.mercadorias.exception.ValorAutonomiaInvalidoException;
import br.com.s2.mercadorias.exception.ValorLitroCombustivelInvalidoException;
import br.com.s2.mercadorias.exception.ValorNuloBrancoException;
import br.com.s2.mercadorias.exception.ValorNumericoFormatoIncorretoException;
import br.com.s2.mercadorias.helper.Helper;
import br.com.s2.mercadorias.pojo.json.MapaJson;
import br.com.s2.mercadorias.service.MapaService;
import br.com.s2.mercadorias.service.RotaService;

@Controller
@RequestMapping("/rest/mapa")
@Scope("request")
public class MapaController {
	
	@Lazy(true)
	@Autowired
	private MapaService mapaService;
	@Lazy(true)
	@Autowired
	private RotaService rotaService;

	/**
	 * inserir lista de mapas e suas rotas json no seguinte formato, por exemplo
	 * [{"nome":"SP","rotas":[{"origem":"A","destino":"B","distancia":10},{"origem":"B","destino":"C","distancia":10}]},{"nome":"RJ","rotas":[{"origem":"A","destino":"B","distancia":13},{"origem":"B","destino":"C","distancia":11.5}]}]
	 * 
	 * @param listaEntrada
	 * @return retorna lista enviada se houve sucesso
	 * @throws ValorNuloBrancoException
	 *             se alguma nome do mapa estah nulo ou branco
	 * @throws MapaSemRotaException
	 *             se nao foi enviada nenhuma rota em algum mapa
	 * @throws OrigemDestinoInvalidoException
	 *             se alguma rota tem origem/destino fora do intervalor A-Z
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/inserir_malha", method = RequestMethod.POST)
	@ResponseBody
	public List<MapaJson> inserirMalha(@RequestBody(required = false) List<MapaJson> listaEntrada ) throws ValorNuloBrancoException, MapaSemRotaException, OrigemDestinoInvalidoException, JsonProcessingException {
		mapaService.inserirMapasJsonComRotas(listaEntrada);
		return listaEntrada;
	}
	
	/**
	 * obtem custo e rota com menor distancia
	 * 
	 * @param params
	 *            json deve ser enviado no seguinte formato, por exemplo
	 *            {"autonomia":11.5,"mapa":"SP","valor_combustivel_litro":2.5,"origem":"A","destino":"B"}
	 * @return retorna custo e rota no seguinte formato {"custo":11.95,
	 *         "rota":"B C D F"}
	 * @throws ParametroJsonNaoEncontradoException
	 *             se faltar campo mapa, origem, destino, autonomia,
	 *             valor_combustivel_litro
	 * @throws ValorNumericoFormatoIncorretoException
	 *             se autonomia ou valor_combustivel_litro em formato incorreto
	 * @throws OrigemDestinoInvalidoException
	 *             se origem/destino fora do intervalor A-Z
	 * @throws JsonProcessingException
	 * @throws RotaNaoDisponivelException
	 *             se nenhuma rota foi encontrada no mapa, origem e destinos
	 *             informados
	 * @throws ValorAutonomiaInvalidoException
	 *             se autonomia <= 0
	 * @throws ValorLitroCombustivelInvalidoException
	 *             se valor litro combustivel <= 0
	 * @throws ValorNuloBrancoException
	 *             se campo mapa = null ou branco
	 */
	@RequestMapping(value = "/obtem_caminho_entrega", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> obtemCaminhoEntrega(@RequestBody(required = false) Map<String, Object> params) throws ParametroJsonNaoEncontradoException, ValorNumericoFormatoIncorretoException, OrigemDestinoInvalidoException, JsonProcessingException, RotaNaoDisponivelException, ValorAutonomiaInvalidoException, ValorLitroCombustivelInvalidoException, ValorNuloBrancoException{
		String mapa = String.valueOf(Helper.getHttpParam(params, "mapa"));
		String origem = String.valueOf(Helper.getHttpParam(params, "origem"));
		String destino = String.valueOf(Helper.getHttpParam(params, "destino"));
		float autonomia;
		
		try {
			autonomia = Float.valueOf(String.valueOf(Helper.getHttpParam(params, "autonomia")));
		} catch (NumberFormatException e) {
			throw new ValorNumericoFormatoIncorretoException("autonomia", (String) Helper.getHttpParam(params, "autonomia"));
		}
		
		float valorCombustivelLitro;
		
		try {
			valorCombustivelLitro = Float.valueOf(String.valueOf( Helper.getHttpParam(params, "valor_combustivel_litro")));
		} catch (NumberFormatException e) {
			throw new ValorNumericoFormatoIncorretoException("valor_combustivel_litro", (String) Helper.getHttpParam(params, "valor_combustivel_litro"));
		}	

		return rotaService.obtemCaminhoMenorCusto(mapa, origem, destino, autonomia, valorCombustivelLitro);
	}
}
