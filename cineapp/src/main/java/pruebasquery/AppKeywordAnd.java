package pruebasquery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import net.itinajero.app.model.Noticia;
import net.itinajero.app.repository.NoticiasRepository;

public class AppKeywordAnd {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
		NoticiasRepository repo = context.getBean("noticiasRepository", NoticiasRepository.class);	
		
		// Ejemplo Keyword And		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//formato que maneja mysql
		List<Noticia> lista=null;
		try {
			lista = repo.findByEstatusAndFecha("Activa", format.parse("2020-04-02"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		for (Noticia n: lista) {
			System.out.println(n);
		}
				
		context.close();
	}

}
