package br.com.s2.mercadorias.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.s2.mercadorias.dao.GenericDao;
import br.com.s2.mercadorias.helper.LoggerFactory;
import br.com.s2.mercadorias.pojo.GenericPojo;

@Service
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class GenericService<T extends GenericPojo> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Lazy(true)
	@Autowired
	@Qualifier("genericDaoMysql")
	private GenericDao<T> dao;	

	@Transactional(propagation = Propagation.REQUIRED)
	public T inserir(T obj) {
		return dao.inserir(obj);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public T alterar(T obj) {
		return dao.alterar(obj);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public  void delete(T obj) {
		dao.delete(obj);
	}
	
	public T detach(T obj) throws CloneNotSupportedException {
		return dao.detach(obj);
	}
}
