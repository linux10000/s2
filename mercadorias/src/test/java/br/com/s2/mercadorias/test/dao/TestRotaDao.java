package br.com.s2.mercadorias.test.dao;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.com.s2.mercadorias.dao.RotaDao;
import br.com.s2.mercadorias.pojo.Rota;

public class TestRotaDao extends TestGenericDao {

	@Lazy(true)
	@Autowired
	private RotaDao rotaDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testObtemRotaPorOrigemDestino() {
		List<Rota> lista = rotaDao.obtemRotaPorOrigemDestino(65, 66, "SEMNOME");
		assertTrue(lista != null);
	}

	@Test
	public void testObtemRotaPorOrigem() {
		List<Rota> lista = rotaDao.obtemRotaPorOrigem(65, BigInteger.ZERO, 10, 0);
		assertTrue(lista != null);
	}

}
