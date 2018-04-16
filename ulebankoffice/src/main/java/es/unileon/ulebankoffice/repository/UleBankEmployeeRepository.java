/**
 * 
 */
package es.unileon.ulebankoffice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.unileon.ulebankoffice.domain.UleBankEmployeeDomain;

/**
 * @author Razvan Raducu
 *
 */
@Repository
public interface UleBankEmployeeRepository extends MongoRepository<UleBankEmployeeDomain, String> {
	
	public UleBankEmployeeDomain findByUserName(String username);
	
	public List<UleBankEmployeeDomain> findByRole(String role);
	
}
