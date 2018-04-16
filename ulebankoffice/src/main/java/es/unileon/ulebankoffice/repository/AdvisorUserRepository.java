/**
 * 
 */
package es.unileon.ulebankoffice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.unileon.ulebankoffice.domain.AdvisorUserDomain;

/**
 * @author Razvan Raducu
 *
 */
public interface AdvisorUserRepository extends MongoRepository<AdvisorUserDomain, String> {
	
	public AdvisorUserDomain findByEmail(String email);
	
}
