package es.unileon.ulebankoffice.web;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.unileon.ulebankoffice.domain.HipotecaInversaDomain;
import es.unileon.ulebankoffice.service.HipotecaInversa;

@Controller
public class HipotecaInversaFormController {
	
	@RequestMapping(value = "/reversemortgage", method = RequestMethod.POST)
    public ModelAndView processAdd(@ModelAttribute("hipotecaInversa") @Valid 
    		HipotecaInversa hipotecaInversa, BindingResult bindingResult, HttpServletResponse response) {
		
		ModelAndView model = new ModelAndView("reversemortgage");
		
		if (bindingResult.hasErrors())
			return model;
		
		double vT = hipotecaInversa.getValorTasacion();
		int e = hipotecaInversa.getEdad();
		double pST = hipotecaInversa.getPorcentajeSobreTasacion();
		double tIP = hipotecaInversa.getTipoInteresPrestamo();
		double cA = hipotecaInversa.getComisionApertura();
		double rR = hipotecaInversa.getRentabilidadRenta();
		double cT = hipotecaInversa.getCosteTasacion();
		double gF = hipotecaInversa.getGastosFormalizacion();
		
		HipotecaInversaDomain myHipotecaInversa = 
				new HipotecaInversaDomain(vT, e, pST, tIP, cA, rR, cT, gF);
		
		List<List<String>> tabla = myHipotecaInversa.calcularTabla();
		
		model.addObject("tabla", tabla);
		
		response.addCookie(new Cookie("resultados", "1"));

        return model;
	}
	
	@RequestMapping(value = "/reversemortgage", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("hipotecaInversa", new HipotecaInversa());
        
        return "reversemortgage";
    }
}
