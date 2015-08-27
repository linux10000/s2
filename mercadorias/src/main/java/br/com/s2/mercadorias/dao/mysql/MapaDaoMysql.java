package br.com.s2.mercadorias.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.com.s2.mercadorias.dao.MapaDao;
import br.com.s2.mercadorias.pojo.Mapa;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class MapaDaoMysql extends GenericDaoMysql<Mapa> implements MapaDao {

	@Lazy(true)
	@PersistenceContext(unitName = "unitPrincipal")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Mapa> obtemMapasPorNome(String nome) {
		List<Mapa> r = new ArrayList<Mapa>();
		
		StringBuilder sb = new StringBuilder();
		sb.append(" Select mapa ");
		sb.append(" from Mapa mapa ");
		sb.append(" where mapa." + Mapa._mapcdescri +" = :nome");
		
		r = em.createQuery(sb.toString()).setParameter("nome", nome).getResultList(); 
		return r;
	}

}
