package es.unileon.ulebankoffice.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.unileon.ulebankoffice.domain.SolicitudFinancialAdvisorDomain;
import es.unileon.ulebankoffice.repository.SolicitudesFinancialAdvisorRepository;

@Controller
public class OffersConsultingController {
	
	@Autowired
	private SolicitudesFinancialAdvisorRepository repo;
	
	private static final Logger logger = Logger.getLogger("ulebankLogger");

	@RequestMapping(value= "/consuLogin", method = RequestMethod.GET) 
	public String getLogin(){
		return "offersConsultingLogin";
	}

	@RequestMapping(value = "/offersconsulting", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest req, HttpServletResponse resp, Principal principal) throws IOException {
		//logger.info(principal.getName() + " " + req.getRemoteAddr() + " ha accedido a la página del financial advisor.");
		List<SolicitudFinancialAdvisorDomain> lists;
		if(hasRole("ROLE_ADMIN") || hasRole("ROLE_SUPERVISOR") || hasRole("ROLE_EMPLEADO")){
			lists = repo.findAllByOrderByEstadoDesc();
			logger.info("Mostrando todas las consultas, las pendientes primero.");
		} else {
			lists = repo.findByEmailOrderByEstadoDesc(req.getUserPrincipal().getName());
			logger.info("Mostrando sólo sus consultas, las pendientes primero.");
		}
		
			model.addAttribute("lists", lists);
						
			model.addAttribute("nombre", req.getUserPrincipal().getName());
		

			return "offersconsulting";
		}
	
	private boolean hasRole(String role) {
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}

		

	
}
