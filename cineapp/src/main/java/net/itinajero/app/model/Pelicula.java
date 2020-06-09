package net.itinajero.app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="Peliculas")  // nombre de la tabla en DB
public class Pelicula {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_incremen MYSQL
	private int id;
	private String titulo;
	private String clasificacion;
	private int duracion;
	private String genero;
	private String imagen = "cinema.png"; //imagen por degfault
	private Date fechaEstreno;
	private String estatus="Activa";
	
	//@Transient//ignorar este atributo durante la persistencia
	// no debe ser persistente tampoco intentara recupearralo cuando utiñizamos byAll
	// Relacion Uno a Uno -> Una pelicula tiene un registro de detalle
	@OneToOne
	@JoinColumn(name="idDetalle") // foreignKey en la tabla de Peliculas
	private Detalle detalle;
	/* En realidad en la aplicacion, no se ocupa la consulta de TODOS los horarios por idPelicula.
	 * La consulta que se ocupa es TODOS LOS HORARIOS POR FECHA para una determinada pelicula.
	 * Por eso, dejamos comentada dicha relacion, aqui en la clase Pelicula.
	 * Con esto nos evitamos un left outer join Horarios on pelicula.id=horarios.idPelicula 
	 */
	// Relacion Uno a Muchos -> Una pelicula tiene muchos horarios
	@OneToMany(mappedBy = "pelicula",fetch = FetchType.EAGER)   //    asi estaba en ss
	private List<Horario> horarios;  							//    asi estaba en ss
		
	
	public List<Horario> getHorarios() {     					//    asi estaba en ss 
		return horarios; 										//    asi estaba en ss
	} 															//    asi estaba en ss
	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}
	public Detalle getDetalle() {
		return detalle;
	}
	public void setDetalle(Detalle detalle) {
		this.detalle = detalle;
	}
	public Pelicula() {
		System.out.println("constructor Pelicula");
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
		this.titulo = titulo;
	}
	public String getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Date getFechaEstreno() {
		return fechaEstreno;
	}
	public void setFechaEstreno(Date fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", titulo=" + titulo + ", clasificacion=" + clasificacion + ", duracion="
				+ duracion + ", genero=" + genero + ", imagen=" + imagen + ", fechaEstreno=" + fechaEstreno
				+ ", estatus=" + estatus + ", detalle=" + detalle + "]";
	}
	
	
	

}
