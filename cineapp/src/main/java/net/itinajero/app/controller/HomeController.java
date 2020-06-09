package net.itinajero.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.itinajero.app.model.Banner;
import net.itinajero.app.model.Horario;
import net.itinajero.app.model.Noticia;
import net.itinajero.app.model.Pelicula;
import net.itinajero.app.service.IBannersService;
import net.itinajero.app.service.IHorariosService;
import net.itinajero.app.service.INoticiasService;
import net.itinajero.app.service.IPeliculasService;
import net.itinajero.app.util.Utileria;

@Controller
public class HomeController {
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IBannersService serviceBanners;
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IPeliculasService servicePeliculas;
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IHorariosService serviceHorarios;
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private INoticiasService serviceNoticias;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	/**
	 * Metodo para mostrar la pagina principal de la aplicacion
	 * @param model
	 * @return
	 */
	
//	@RequestMapping(value="/", method=RequestMethod.GET)
//	public String mostrarPrincipal(Model modelo) {
//		
//		List<String> listaFechas = Utileria.getNextDays(4);
//		//System.out.println(listaFechas);
//		
//		List<Pelicula> peliculas= servicePeliculas.buscarTodas();
//
//		modelo.addAttribute("fechas", listaFechas);
//		modelo.addAttribute("fechaBusqueda", dateFormat.format(new Date()));
//		modelo.addAttribute("peliculas", peliculas);
//		modelo.addAttribute("banners", serviceBanners.buscarTodos());
//		System.out.println("en banners pero en / metodo GET");
//		return "home";
//	}
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String mostrarPrincipal(Model model) {	

		try {
			Date fechaSinHora = dateFormat.parse(dateFormat.format(new Date()));
			List<String> listaFechas = Utileria.getNextDays(4);		
			List<Pelicula> peliculas = servicePeliculas.buscarPorFecha(fechaSinHora);			
			model.addAttribute("fechas", listaFechas);
			model.addAttribute("fechaBusqueda", dateFormat.format(new Date()));
			model.addAttribute("peliculas", peliculas);
		} catch (ParseException e) {
			System.out.println("Error: HomeController.mostrarPrincipal" + e.getMessage());
		}
		return "home";

	}	
	/**
	 * Metodo para filtrar las peliculas por fecha
	 * @param fecha
	 * @param model
	 * @return
	 */	//Nuevo METODO
//	@RequestMapping(value="/search",method = RequestMethod.POST)
//	public String buscar(@RequestParam("fecha") String fecha, Model modelo) { //cambiamos String por Date
//		List<String> listaFechas = Utileria.getNextDays(4);
//		//System.out.println(listaFechas);
//		
//		//List<Pelicula> peliculas= getLista();
//		List<Pelicula> peliculas= servicePeliculas.buscarTodas();
//		modelo.addAttribute("fechas", listaFechas);
//		modelo.addAttribute("fechaBusqueda", fecha);//esta fecha es la que el usuario selecciono
//		modelo.addAttribute("peliculas", peliculas);
//		modelo.addAttribute("banners",serviceBanners.buscarTodos());
//		System.out.println(" /search Buscando todas las peliculas en exhibicion para la fecha: "+fecha);
//		return "home";
//	}
	
	@RequestMapping(value = "/search", method=RequestMethod.POST)
	public String buscar(@RequestParam("fecha") Date fecha, Model model) {		
		try {			
			Date fechaSinHora = dateFormat.parse(dateFormat.format(fecha));
			List<String> listaFechas = Utileria.getNextDays(4);
			List<Pelicula> peliculas  = servicePeliculas.buscarPorFecha(fechaSinHora);
			model.addAttribute("fechas", listaFechas);			
			// Regresamos la fecha que selecciono el usuario con el mismo formato
			model.addAttribute("fechaBusqueda",dateFormat.format(fecha));			
			model.addAttribute("peliculas", peliculas);			
			return "home";
		} catch (ParseException e) {
			System.out.println("Error: HomeController.buscar" + e.getMessage());
		}
		return "home";
	}
	
	
//		@RequestMapping(value="/home", method=RequestMethod.GET)
//	public String goHome(){
//		return "home";
//	}
	
	//estudiar esto
//  //@RequestMapping(value="/detail/{id}/{fecha}", method = RequestMethod.GET) // con parametros dinamicos	
//	@RequestMapping(value="/detail", method = RequestMethod.GET) 
//	   //public String mostrarDetalle(Model modelo, @PathVariable("id") int idPelicula, @PathVariable("fecha") String fecha) {
//	public String mostrarDetalle(Model modelo, @RequestParam("idMovie") int idPelicula, @RequestParam("fecha") Date fecha) {	//antes estaba String fecha se cambio a Date fecha
//		List<Horario> horarios= serviceHorarios.buscarPorIdPelicula(idPelicula, fecha);
//		modelo.addAttribute("horarios", horarios);
//		modelo.addAttribute("fechaBusqueda", dateFormat.format(fecha));
//		System.out.println("Buuuuuscando horarios para la pelicula: "+idPelicula);//se utilizara idPelicula para hacer una busqueda a base de datos
//		System.out.println("para la fecha: "+ fecha);
//		modelo.addAttribute("pelicula", servicePeliculas.buscarPorId(idPelicula));
//		
//		// TODO - Buscar en la base de datos los horarios 
////		String tituloPelicula ="Rapidos y furiosos";
////		int duracion = 136;
////		double precioEntrada = 50;
////		modelo.addAttribute("titulo",tituloPelicula);
////		modelo.addAttribute("precio", precioEntrada);
//		return "detalle";		
//	}
	/**
	 * Metodo para ver los detalles y horarios de una pelicula
	 * @param idPelicula
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "/detail/{id}/{fecha}")
	public String mostrarDetalle(@PathVariable("id") int idPelicula, @PathVariable("fecha") Date fecha, Model model) {
		// TODO - Buscar en la base de datos los horarios.		
		List<Horario> horarios= serviceHorarios.buscarPorIdPelicula(idPelicula, fecha);
		model.addAttribute("horarios", horarios);
		model.addAttribute("fechaBusqueda", dateFormat.format(fecha));
		model.addAttribute("pelicula", servicePeliculas.buscarPorId(idPelicula));		
		return "detalle";
	}
	
	//hay que personalizar nuestra fecha entonces agregamos:
	//mosrar cerca
	//mostrar login
	
	@RequestMapping(value = "/formLogin", method = RequestMethod.GET)
	public String mostrarLogin() {		
		return "formLogin";
	}
	
	/**
	 * Metodo que muestra la vista de la pagina de Acerca
	 * @return
	 */
	@RequestMapping(value = "/about")
	public String mostrarAcerca() {			
		return "acerca";
	}
	
	@ModelAttribute("noticias")
	public List<Noticia> getNoticias(){
		return serviceNoticias.buscarUltimas();
	}
	
	@ModelAttribute("banners")
	public List<Banner> getBanners(){
		return serviceBanners.buscarActivos();
	}
	
	/**
	 * Metodo para personalizar el Data Binding para los atributos de tipo Date
	 * @param webDataBinder
	 */
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	// Metodo para generar una lista de Objetos de Modelo (Pelicula)
	//	private List<Pelicula> getLista() {
		
}
