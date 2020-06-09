package net.itinajero.app.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.app.model.Pelicula;
import net.itinajero.app.service.IDetallesService;
import net.itinajero.app.service.IPeliculasService;
import net.itinajero.app.util.Utileria;

@Controller
@RequestMapping(value="/peliculas")
public class PeliculasController {

	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IDetallesService serviceDetalles;
	
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IPeliculasService servicePeliculas;
    /**
	 * Metodo que muestra la lista de peliculas
	 * @param model
	 * @return
	 */	
	@GetMapping(value="/index")
	public String mostrarIndex(Model model) {
		List<Pelicula> lista = servicePeliculas.buscarTodas();
		model.addAttribute("peliculas", lista);
		return "peliculas/listPeliculas";
	}
	/**
	 * Metodo que muestra la lista de peliculas con paginacion
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Pelicula> lista = servicePeliculas.buscarTodas(page);
		model.addAttribute("peliculas", lista);
		return "peliculas/listPeliculas";
	}

	/**
	 * Metodo para mostrar el formulario para crear una pelicula
	 * @return
	 */	
	@GetMapping(value="/create")
	public String crear(@ModelAttribute Pelicula pelicula, Model model) {
		//model.addAttribute("generos", servicePeliculas.buscarGeneros());
		return "peliculas/formPelicula";
	}
	/**
	 * Metodo para guardar los datos de la pelicula (CON ARCHIVO DE IMAGEN)
	 * @param pelicula
	 * @param result
	 * @param model
	 * @param multiPart
	 * @param request
	 * @return
	 */
//	@PostMapping(value="/save")
//	public String guardar(@ModelAttribute Pelicula pelicula, BindingResult result,RedirectAttributes attributes,
//			@RequestParam("archivoImagen") MultipartFile multiPart, HttpServletRequest request ){
//		
//		if (result.hasErrors()) {
//			System.out.println("Existieron errores");
//			return "peliculas/formPelicula";
//		}
//		
////		for (ObjectError error:result.getAllErrors()) {
////			System.out.println(error.getDefaultMessage());
////		}
//		System.out.println("REcibiendo objeto pelicula: " +pelicula);
//		
//		if (!multiPart.isEmpty()) {
//			String nombreImagen = Utileria.guardarImagen(multiPart,request);
//			pelicula.setImagen(nombreImagen);
//		}
//		
//		System.out.println("objeto detalle antes de la insercion: "+pelicula.getDetalle());
//		serviceDetalles.insertar(pelicula.getDetalle());
//		System.out.println("objeto detalle despues de la insercion: "+pelicula.getDetalle());
//		//System.out.println("Elementos de la lista antes de la insercion: " +servicePeliculas.buscarTodas().size());
//		
//		servicePeliculas.insertar(pelicula);
//		
//		//System.out.println("Elementos de la lista despues de la insercion: " +servicePeliculas.buscarTodas().size());
//
//		//model.addAttribute("mensaje", "El registro fue guardado");
//		
//		attributes.addFlashAttribute("mensaje", "El registro fue guardado");
//
//		//return "peliculas/formPelicula";
//		return "redirect:/peliculas/index";
//	}  NUEVO /save
	@PostMapping(value = "/save")
	public String guardar(@ModelAttribute Pelicula pelicula, BindingResult result, Model model,
			@RequestParam("archivoImagen") MultipartFile multiPart, HttpServletRequest request, RedirectAttributes attributes) {	
		
		if (result.hasErrors()){
			
			System.out.println("Existieron errores");
			return "peliculas/formPelicula";
		}	
		
		if (!multiPart.isEmpty()) {
			String nombreImagen = Utileria.guardarImagen(multiPart,request);
			if (nombreImagen!=null){ // La imagen si se subio				
				pelicula.setImagen(nombreImagen); // Asignamos el nombre de la imagen
			}	
		}
		
		// Primero insertamos el detalle
	    serviceDetalles.insertar(pelicula.getDetalle());
	    
		// Como el Detalle ya tiene id, ya podemos guardar la pelicula
		servicePeliculas.insertar(pelicula);
		attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron guardados!");
			
		//return "redirect:/peliculas/index";
		return "redirect:/peliculas/indexPaginate";		
	}	
	/**
	 * Metodo que muestra el formulario para editar una pelicula
	 * @param idPelicula
	 * @param model
	 * @return
	 */
	@GetMapping(value="/edit/{id}")
	public String editar(@PathVariable("id") int idPelicula, Model model) {
		//model.addAttribute("generos", servicePeliculas.buscarGeneros());
		Pelicula pelicula = servicePeliculas.buscarPorId(idPelicula);
		model.addAttribute("pelicula", pelicula);
		return "peliculas/formPelicula"; 
		
	}
	/**
	 * Metodo para eliminar una pelicula
	 * @param idPelicula
	 * @param attributes
	 * @return
	 */
	//METODO ELIMINAR
	@GetMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable("id") int idPelicula, RedirectAttributes attributes) {
		// Buscamos primero la pelicula
		Pelicula pelicula =servicePeliculas.buscarPorId(idPelicula);
		// Eliminamos la pelicula. Tambien al borrar la pelicula, se borraran los Horarios (ON CASCADE DELETE)
		servicePeliculas.eliminar(idPelicula);
		// Eliminamos el registro del detalle
		serviceDetalles.eliminar(pelicula.getDetalle().getId());
		attributes.addFlashAttribute("msg","la pelicula fue eliminada !");
		//return "redirect:/peliculas/index";
		return "redirect:/peliculas/indexPaginate";
	}
	/**
	 * Agregamos al Model la lista de Generos: De esta forma nos evitamos agregarlos en los metodos
	 * crear y editar. 
	 * @return
	 */
	@ModelAttribute("generos") //ATENCION VA A ESTAR DISPONIBLE PARA LOS METODOS DE ESTE CONTROLADOR 
	public List<String> getGeneros(){
		return servicePeliculas.buscarGeneros();
	}
	
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	  
	  binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
