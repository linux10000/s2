package br.com.s2.mercadorias.pojo;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.proxy.HibernateProxy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="rota")
public class Rota extends GenericPojo {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="rotnid", unique=true, nullable=false, columnDefinition = "BIGINT")
	private BigInteger rotnid;
    @Column(name = "rotnorigem",nullable = false )
	private Integer rotnorigem;
    @Column(name = "rotndestino", nullable = false )
	private Integer rotndestino;
    @Column(name = "rotcdistancia", nullable = false )
	private Integer rotcdistancia;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mapnid", nullable=false)
	@JsonInclude(Include.NON_NULL)
	private Mapa mapa;

	public Rota() {
		super();
	}
	
	public Rota(BigInteger rotnid, Integer rotnorigem, Integer rotndestino,
			Integer rotcdistancia, Mapa mapa) {
		super();
		this.rotnid = rotnid;
		this.rotnorigem = rotnorigem;
		this.rotndestino = rotndestino;
		this.rotcdistancia = rotcdistancia;
		this.mapa = mapa;
	}

	public BigInteger getRotnid() {
		return rotnid;
	}
	public void setRotnid(BigInteger rotnid) {
		this.rotnid = rotnid;
	}
	public Integer getRotnorigem() {
		return rotnorigem;
	}
	public void setRotnorigem(Integer rotnorigem) {
		this.rotnorigem = rotnorigem;
	}
	public Integer getRotndestino() {
		return rotndestino;
	}
	public void setRotndestino(Integer rotndestino) {
		this.rotndestino = rotndestino;
	}
	public Integer getRotcdistancia() {
		return rotcdistancia;
	}
	public void setRotcdistancia(Integer rotcdistancia) {
		this.rotcdistancia = rotcdistancia;
	}
	public Mapa getMapa() {
       HibernateProxy proxy = null;

       try {
       	  proxy = HibernateProxy.class.cast(this.mapa);
       } catch (Exception ex){
       	  return this.mapa;
       }

       if ( proxy != null && proxy.getHibernateLazyInitializer().isUninitialized() ) 
       	  return null;
       else
       	  return this.mapa;
	}
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	@Override
	public Object getChave() {
		return rotnid;
	}
	@Override
	public String getFieldNameChave() {
		return "rotnid";
	}
	
	public static final String _rotnid = "rotnid";
	public static final String _rotnorigem = "rotnorigem";
	public static final String _rotndestino = "rotndestino";
	public static final String _rotcdistancia = "rotcdistancia";
	public static final String _mapa = "mapa";
}
