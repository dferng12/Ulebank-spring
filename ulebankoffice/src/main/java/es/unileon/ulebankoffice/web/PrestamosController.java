package es.unileon.ulebankoffice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Razvan Raducu
 *
 */
@Controller
public class PrestamosController {

	@RequestMapping(value={"/loans"})
	public String controllerVacio(){
		return "prestamos";
	}
	
}