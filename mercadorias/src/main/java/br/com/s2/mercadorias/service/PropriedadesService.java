package br.com.s2.mercadorias.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.s2.mercadorias.dao.PropriedadesDao;
import br.com.s2.mercadorias.pojo.Propriedades;

@Service
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class PropriedadesService {

	public void inserir(Propriedades pro) throws Exception{
		new PropriedadesDao().inserir(pro);
	}
	
	public Propriedades ler() throws Exception{
		return new PropriedadesDao().ler();
	}
}
