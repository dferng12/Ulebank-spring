/**
 * 
 */
package es.unileon.ulebankoffice.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.unileon.ulebankoffice.domain.TAEVariosInteresesDomain;
import es.unileon.ulebankoffice.service.TAEVariosIntereses;

/**
 * @author Razvi Razvan Raducu
 *
 */
@Controller
public class TAEVariosInteresesController {

	@RequestMapping(value = "/aprv", method = RequestMethod.GET)
	public String getAprvView(@ModelAttribute("datosTaeVariosIntereses") TAEVariosIntereses taeVariosIntereses) {
		return "aprv";
	}

	@RequestMapping(value = "/aprv", method = RequestMethod.POST)
	public String calculateAprv(ModelMap model,
			@ModelAttribute("datosTaeVariosIntereses") @Valid TAEVariosIntereses taeVariosIntereses,
			BindingResult result, HttpServletResponse response) {

		// Finalmente parece ser que sí que hay que contemplar intereses
		// negativos, así que deshabilito el validator. Se podría hacer con
		// wiring @Autowire si se añade @Component en la clase
		// TAEVariosInteresesValidaro
		// TAEVariosInteresesValidator taeValidator = new
		// TAEVariosInteresesValidator();
		// taeValidator.validate(taeVariosIntereses, result);

		if (result.hasErrors()) {
			return "aprv";
		}

		TAEVariosInteresesDomain tae = new TAEVariosInteresesDomain(taeVariosIntereses.getPeriodo(),
				taeVariosIntereses.getIntereses());

		String TAE = tae.calcular();

		model.addAttribute("TAE", TAE);

		response.addCookie(new Cookie("resultados", "1"));

		return "aprv";
	}
}
