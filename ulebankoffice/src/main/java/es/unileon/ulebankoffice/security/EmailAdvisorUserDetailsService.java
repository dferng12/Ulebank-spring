package es.unileon.ulebankoffice.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import es.unileon.ulebankoffice.domain.AdvisorUserDomain;
import es.unileon.ulebankoffice.repository.AdvisorUserRepository;

/**
 * @author Razvan Raducu
 *
 */
@Component
public class EmailAdvisorUserDetailsService implements UserDetailsService {

	@Autowired
	private AdvisorUserRepository repo;

	private User userDetails;

	private static final Logger logger = Logger.getLogger("ulebankLogger");

	@Override
	public UserDetails loadUserByUsername(String userName) {

		if (!userName.contains("@") || !userName.contains(".")) {
			logger.warn("Alguien ha tratado de acceder al consultor financiero sin ser un email. Nombre intentado: "
					+ userName);
			throw new UsernameNotFoundException("Se debe buscar un e-mail");
		}

		/*
		 * Si el último indice de "." punto está antes que la "@" arroba, el
		 * email es incorrecto.
		 */

		if (userName.lastIndexOf('.') < userName.indexOf('@')) {
			logger.warn(
					"Alguien ha tratado de acceder al consultor financiero sin ser un email correcto. Nombre intentado: "
							+ userName);
			throw new UsernameNotFoundException("El email tratado de buscar no es correcto");
		}
		
		/*
		 * Es en este punto donde se crean los usuarios que acceden por primera vez.
		 */
		AdvisorUserDomain clienteAdvisor = repo.findByEmail(userName);
		if (clienteAdvisor == null) {
			logger.info(
					"Un nuevo email ha accedido al consultor financiero:" + userName + " Creando registro de usuario.");
			clienteAdvisor = new AdvisorUserDomain();
			clienteAdvisor.setEmail(userName);
			repo.save(clienteAdvisor);
		}

		userDetails = new User(clienteAdvisor.getEmail(), "", getAuthorities());
		logger.info("Alguien ha tratado de inciar sesión en el consultor financiero con el email:" + userName + " .");
		return userDetails;
	}

	private List<GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authList = new ArrayList<>();

		authList.add(new SimpleGrantedAuthority("ROLE_ADVISORUSER"));

		return authList;
	}

}
