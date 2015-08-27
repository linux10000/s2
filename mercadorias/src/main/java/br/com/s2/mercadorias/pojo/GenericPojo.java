package br.com.s2.mercadorias.pojo;

import java.io.Serializable;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true, value = "fieldHandler")
public abstract class GenericPojo implements Serializable, Cloneable {

	@Transient
	private Object chave;
	private static final long serialVersionUID = 1L;
	public abstract Object getChave();
	public abstract String getFieldNameChave();

	 public boolean equals(Object other) {
	    return other instanceof GenericPojo && (this.getChave() != null) ? this.getChave().equals(((GenericPojo) other).getChave()) : (other == this);
	 }
	
	public int hashCode() {
	    return getChave() != null ? this.getClass().hashCode() + getChave().hashCode() : super.hashCode();
	}	

	@Override  
    public Object clone() throws CloneNotSupportedException {  
        Object copia = super.clone();
        return copia;  
    } 
	

}
