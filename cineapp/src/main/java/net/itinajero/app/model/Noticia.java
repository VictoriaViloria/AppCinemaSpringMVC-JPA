/**
 *  Clase de modelo que representa una noticia en la seccion Noticias / Novedades de la pagina principal
 */
package net.itinajero.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //de javax.persistence
@Table(name = "Noticias")
public class Noticia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // oracle secrets, auto_increment MySQL
	private int id;
	private String titulo;
	private Date fecha;
	private String detalle;
	private String estatus;

	/**
	 * Constructor sin parametros
	 */
	public Noticia() {
		System.out.println("constructor de Noticia ");
		this.fecha=new Date();
		this.estatus="Activa";		
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		System.out.println("estoy en setTitulo ");
		this.titulo = titulo;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		System.out.println("setDetalle");
		this.detalle = detalle;
	}


	public String getEstatus() {
		return estatus;
	}


	public void setEstatus(String estatus) {
		System.out.println("setEstatus");
		this.estatus = estatus;
	}


	@Override
	public String toString() {
		return "Noticia [id=" + id + ", titulo=" + titulo + ", fecha=" + fecha + ", detalle=" + detalle + ", estatus="
				+ estatus + "]";
	}

}
