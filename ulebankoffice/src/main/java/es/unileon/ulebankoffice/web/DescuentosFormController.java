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

import es.unileon.ulebankoffice.domain.DescuentosDomain;
import es.unileon.ulebankoffice.service.Descuentos;

@Controller
public class DescuentosFormController {
	
	@RequestMapping(value = "/discounts", method = RequestMethod.POST)
    public ModelAndView processAdd(@Valid @ModelAttribute("descuentos")
    		Descuentos descuentos, BindingResult bindingResult, HttpServletResponse response) {
		
		ModelAndView model = new ModelAndView("discounts");
		
		if (bindingResult.hasErrors())
        	return model;
		
		double importeDescuento = descuentos.getImporteDescuento();
		int periodoDescuento = descuentos.getPeriodoDescuento();
		double tipoInteres = descuentos.getTipoInteres();
		int base = descuentos.getBase();
		double otrosGastos = descuentos.getOtrosGastos();
		double comisiones = descuentos.getComisiones();
		
		DescuentosDomain myDescuentos = 
				new DescuentosDomain(importeDescuento, periodoDescuento, tipoInteres, base, otrosGastos, comisiones);
		
		List<List<String>> tabla = myDescuentos.calcularTabla();
		
		model.addObject("tabla", tabla);
        
        response.addCookie(new Cookie("resultados", "1"));

        return model;	
	}
	
	@RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("descuentos", new Descuentos());
        
        return "discounts";
    }	

}
