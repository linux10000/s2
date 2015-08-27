package br.com.s2.mercadorias.dao.mysql;

import java.lang.reflect.ParameterizedType;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import br.com.s2.mercadorias.dao.GenericDao;
import br.com.s2.mercadorias.pojo.GenericPojo;

@Repository
public class GenericDaoMysql<T extends GenericPojo> implements GenericDao<T> {
	protected final Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Lazy(true)
	@PersistenceContext(unitName = "unitPrincipal")
	private EntityManager em;
	
	public GenericDaoMysql(){
		
	}
	
	public Object obtemProxSeqGen(String nomeGenerator, int incremento){
		StringBuilder sb = new StringBuilder();
		sb.append(" Select gen_id(" + nomeGenerator +", " + String.valueOf(incremento) + ") from rdb$database ");				
		
		Object r = em.createNativeQuery(sb.toString()).getSingleResult();
		
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public T detach(T obj) throws CloneNotSupportedException{
		T r = (T) obj.clone();
		em.detach(r);
		return r;
	}

	public T inserir(T obj) {
		em.persist(obj);
		return obj;
	}
	
	public T alterar(T obj) {
		T t = em.merge(obj);
		
		return t;
	}
	
	public void delete(T obj){
		
		StringBuilder sb = new StringBuilder();
//		sb.append(" Delete from ").append(classe.getSimpleName());
		sb.append(" Delete from ").append(obj.getClass().getSimpleName());
		sb.append(" where ").append(((GenericPojo) obj).getFieldNameChave()).append(" = ").append(((GenericPojo) obj).getChave());
		
		em.createQuery(sb.toString()).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public T obtemPorId(Object id) {
//		T t = em.find( classe, id);
		
		Class<T> classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		T t = em.find( classe, id);
		
		return t;
	}
}
