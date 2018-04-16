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

import es.unileon.ulebankoffice.domain.TAEDomain;
import es.unileon.ulebankoffice.service.TAE;

@Controller
public class SimuladorTAEFormController {

	@RequestMapping(value = "/apr", method = RequestMethod.POST)
    public ModelAndView processAdd(@ModelAttribute("tae") @Valid TAE tae, BindingResult bindingResult, HttpServletResponse response) {
		
		ModelAndView model = new ModelAndView("aprcalculation");
		
        if (bindingResult.hasErrors())
        	return model;
        
        TAEDomain myTAE = new TAEDomain(tae.getI(), tae.getP());
        
        List<List<String>> tabla = myTAE.calcularTabla();
        
        model.addObject("tabla", tabla);
        
        response.addCookie(new Cookie("resultados", "1"));

        return model;
    }

    @RequestMapping(value = "/apr", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("tae", new TAE());
        
        return "aprcalculation";
    }
}