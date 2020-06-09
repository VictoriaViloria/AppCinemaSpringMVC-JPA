package pruebascrudrepo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.itinajero.app.model.Noticia;
import net.itinajero.app.repository.NoticiasRepository;

//Aplicacion para persistir (crear) en la tabla Noticias un Objeto de tipo Noticia
public class AppCreate {
	public static void main(String[] args) {
	
	Noticia noticia = new Noticia();
	noticia.setTitulo("Proximo Estreno: La casa al final de la Calle)");
	noticia.setDetalle("actor: Daniela Romo");
	
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
	NoticiasRepository repo = context.getBean("noticiasRepository", NoticiasRepository.class);
	
	repo.save(noticia);
	
	context.close();
}
	
	

}
