/**
 * 
 */
package es.unileon.ulebankoffice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.unileon.ulebankoffice.domain.SolicitudFinancialAdvisorDomain;

/**
 * @author Razvan Raducu
 *
 */
public interface SolicitudesFinancialAdvisorRepository extends MongoRepository<SolicitudFinancialAdvisorDomain, String> {
	
		public List<SolicitudFinancialAdvisorDomain> findByEmailOrderByEstadoDesc(String email);
		public List<SolicitudFinancialAdvisorDomain> findAllByOrderByEstadoDesc();
	
}
