package br.com.s2.mercadorias.test.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.com.s2.mercadorias.dao.MapaDao;
import br.com.s2.mercadorias.pojo.Mapa;

public class TestMapaDao extends TestGenericDao {

	@Lazy(true)
	@Autowired
	private MapaDao mapaDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testObtemMapasPorNome() {
		List<Mapa> lista = mapaDao.obtemMapasPorNome("SEMNOME");
		assertTrue(lista != null);
	}

}
