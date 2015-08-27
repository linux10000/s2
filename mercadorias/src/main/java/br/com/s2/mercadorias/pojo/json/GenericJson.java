package br.com.s2.mercadorias.pojo.json;

import java.io.Serializable;

public class GenericJson implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Override  
    public Object clone() throws CloneNotSupportedException {  
        Object copia = super.clone();
        return copia;  
    } 
}
