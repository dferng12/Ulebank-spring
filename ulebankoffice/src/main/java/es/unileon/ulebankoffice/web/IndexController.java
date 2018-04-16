package es.unileon.ulebankoffice.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Razvan Raducu
 *
 */
@Controller
public class IndexController {
	
	
	
	@RequestMapping(value = { "/", "/index","/inicio" })
	public String controllerVacio() {

		
		return "index";
	}
	
}
