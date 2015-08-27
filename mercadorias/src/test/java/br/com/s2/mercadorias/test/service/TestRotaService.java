package br.com.s2.mercadorias.test.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.s2.mercadorias.exception.ValorAutonomiaInvalidoException;
import br.com.s2.mercadorias.exception.ValorLitroCombustivelInvalidoException;
import br.com.s2.mercadorias.exception.ValorNuloBrancoException;
import br.com.s2.mercadorias.pojo.Rota;
import br.com.s2.mercadorias.service.RotaService;
import br.com.s2.mercadorias.test.helper.TesteUnitario;

public class TestRotaService extends TesteUnitario {

	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testObtemRotaHorizontal() throws ValorNuloBrancoException {
		List<Rota> lista = new ArrayList<Rota>();
		lista.add(new Rota(null, 65, 66, 0, null));
		lista.add(new Rota(null, 66, 67, 0, null));
		lista.add(new Rota(null, 67, 70, 0, null));
		
		String r = new RotaService().obtemRotaHorizontal(lista);
		
		assertTrue(r.equals("A B C F"));
	}

	@Test
	public void testCalculaCustoRota() throws ValorAutonomiaInvalidoException, ValorLitroCombustivelInvalidoException, ValorNuloBrancoException {
		float r = new RotaService().calculaCustoRota(20, 5, 10);
		assertTrue(r == 2.5);
	}

}
