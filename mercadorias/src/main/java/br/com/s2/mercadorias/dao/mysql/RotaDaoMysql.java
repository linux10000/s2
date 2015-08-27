package br.com.s2.mercadorias.dao.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.com.s2.mercadorias.dao.RotaDao;
import br.com.s2.mercadorias.pojo.Mapa;
import br.com.s2.mercadorias.pojo.Rota;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class RotaDaoMysql extends GenericDaoMysql<Rota> implements RotaDao {

	@Lazy(true)
	@PersistenceContext(unitName = "unitPrincipal")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Rota> obtemRotaPorOrigemDestino(Integer origem, Integer destino, String mapa) {
		StringBuilder sb = new StringBuilder();
		sb.append(" Select rota ");
		sb.append(" from Rota rota ");
		sb.append(" inner join rota.mapa mapa ");
		sb.append(" where rota." + Rota._rotnorigem +" = :origem ");
		sb.append("   and rota." + Rota._rotndestino + " = :destino ");
		sb.append("   and rota." + Rota._mapa + "." + Mapa._mapcdescri +" = :mapa ");
		
		Query q = em.createQuery(sb.toString());
		q.setParameter("origem", origem);
		q.setParameter("destino", destino);
		q.setParameter("mapa", mapa);
		
		List<Rota> r = q.getResultList();
		
		return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rota> obtemRotaPorOrigem(Integer origem, BigInteger mapnid, int tamanhoPagina, int skip) {
		StringBuilder sb = new StringBuilder();
		sb.append(" Select rota ");
		sb.append(" from Rota rota ");
		sb.append(" where rota." + Rota._rotnorigem +" = :origem ");
		sb.append("   and rota." + Rota._mapa + "." + Mapa._mapnid +" = :mapa ");		
		
		Query q = em.createQuery(sb.toString());
		q.setParameter("origem", origem);
		q.setParameter("mapa", mapnid);
		
		List<Rota> r = q.setFirstResult(skip).setMaxResults(tamanhoPagina).getResultList();
		
		return r;
	}

}
