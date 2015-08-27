package br.com.s2.mercadorias.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.s2.mercadorias.annotation.PropriedadeAnnotation;

public class Propriedades implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Parametro path deve ser preenchido!")
	@PropriedadeAnnotation
	private String path;

	@NotNull(message = "Parametro enoc deve ser preenchido!")
	@PropriedadeAnnotation
	private String usuario;
	
	@NotNull(message = "Parametro enoc deve ser preenchido!")
	@PropriedadeAnnotation
	private String enoc;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getEnoc() {
		return enoc;
	}
	public void setEnoc(String enoc) {
		this.enoc = enoc;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
