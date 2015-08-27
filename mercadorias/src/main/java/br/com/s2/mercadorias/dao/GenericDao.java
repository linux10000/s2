package br.com.s2.mercadorias.dao;

import br.com.s2.mercadorias.pojo.GenericPojo;


public interface GenericDao<T extends GenericPojo> {

	public Object obtemProxSeqGen(String nomeGenerator, int incremento);
	public T inserir(T obj);
	public T alterar(T obj);
	public void delete(T obj);
	public T obtemPorId(Object id);
	public T detach(T obj) throws CloneNotSupportedException;
}
