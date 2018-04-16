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

import es.unileon.ulebankoffice.domain.ArrendamientoFinancieroDomain;
import es.unileon.ulebankoffice.service.ArrendamientoFinanciero;

@Controller
public class ArrendamientoFinancieroFormController {

	@RequestMapping(value = "/leasing", method = RequestMethod.POST)
    public ModelAndView processAdd(@ModelAttribute("arrendamientoFinanciero") @Valid 
    		ArrendamientoFinanciero arrendamientoFinanciero, BindingResult bindingResult, HttpServletResponse response) {
		
		ModelAndView model = new ModelAndView("leasing");
		
		if (bindingResult.hasErrors())
        	return model;
		
		double valorBien = arrendamientoFinanciero.getValorBien();
		int duracionContrato = arrendamientoFinanciero.getDuracionContrato();
		int fraccionamientoPagoCuota = arrendamientoFinanciero.getFraccionamientoPagoCuota();
		double tipoInteres = arrendamientoFinanciero.getTipoInteres();
		
		ArrendamientoFinancieroDomain myArrendamientoFinanciero = 
				new ArrendamientoFinancieroDomain(valorBien, duracionContrato, fraccionamientoPagoCuota, tipoInteres);
		
		List<List<String>> tabla = myArrendamientoFinanciero.calcularTabla();
		
		model.addObject("tabla", tabla);
        
        response.addCookie(new Cookie("resultados", "1"));

        return model;
	}
	
	@RequestMapping(value = "/leasing", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("arrendamientoFinanciero", new ArrendamientoFinanciero());
        
        return "leasing";
    }
}
