package net.itinajero.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.app.model.Pelicula;

public interface IPeliculasService {
	void insertar(Pelicula pelicula);	
	List<Pelicula> buscarTodas();
		
	Pelicula buscarPorId(int idPelicula);
	List<String> buscarGeneros();
	// Con este metodo traemos las peliculas Activas. Para formar el select de Peliculas del FORM de nuevo Horario.
	void eliminar(int idPelicula);
	List<Pelicula> buscarPorFecha(Date fecha);
	List<Pelicula> buscarActivas();
	Page<Pelicula> buscarTodas(Pageable page);
}
