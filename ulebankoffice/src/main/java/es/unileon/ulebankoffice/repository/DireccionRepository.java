/**
 * 
 */
package es.unileon.ulebankoffice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import es.unileon.ulebankoffice.domain.DireccionDomain;

/**
 * @author Razvan Raducu
 *
 */
@Repository
public interface DireccionRepository extends MongoRepository<DireccionDomain, String> {
	
	/**
	 * Busca todas las direcciones asociadas a un cliente en función de su dni.
	 * @param dni del cliente
	 * @return lista de direcciones
	 */
	@Query("{'dni._id':?0}")
	public List<DireccionDomain> findByDni(String dni);
	
	
	/**
	 * Borra todas las direcciones asociadas a un determinado cliente
	 * @param dni de las direcciones a borrar
	 */
	@Query(value="{'dni._id' : ?0}", delete = true)
	public void deleteByDni(String dni);
}
