package br.com.s2.mercadorias.test.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.s2.mercadorias.test.helper.TesteIntegracao;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations ={"file:src/main/resources/mvc-dispatcher-servlet.xml", "file:src/test/resources/applicationContext.xml"})
@ActiveProfiles("teste")
public class TestGenericDao extends TesteIntegracao {

	@Test
	public void test(){
		assertTrue( 4/2 != 0);
	}
}
