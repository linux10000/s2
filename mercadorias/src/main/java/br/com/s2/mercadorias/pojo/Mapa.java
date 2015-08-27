package br.com.s2.mercadorias.pojo;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mapa")
public class Mapa extends GenericPojo {

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="mapnid", unique=true, nullable=false, columnDefinition = "BIGINT")
	private BigInteger mapnid;
    @Column(name = "mapcdescri", length = 100, nullable = false )
	private String mapcdescri;

	public BigInteger getMapnid() {
		return mapnid;
	}
	public void setMapnid(BigInteger mapnid) {
		this.mapnid = mapnid;
	}
	public String getMapcdescri() {
		return mapcdescri;
	}
	public void setMapcdescri(String mapcdescri) {
		this.mapcdescri = mapcdescri;
	}
	@Override
	public Object getChave() {
		return mapnid;
	}
	@Override
	public String getFieldNameChave() {
		return "mapnid";
	}
	
	public static final String _mapnid = "mapnid";
	public static final String _mapcdescri = "mapcdescri";
}
