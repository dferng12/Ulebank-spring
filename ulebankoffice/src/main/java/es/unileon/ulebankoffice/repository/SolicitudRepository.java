/**
 * 
 */
package es.unileon.ulebankoffice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.unileon.ulebankoffice.domain.SolicitudDomain;

/**
 * @author Razvi Razvan Raducu
 *
 */
@Repository
public interface SolicitudRepository extends MongoRepository<SolicitudDomain, String> {

	/**
	 * Método encargado de obtener la solicitud asociada a un producto.
	 * 
	 * @param productId
	 *            Id del producto cuya solicitud se quiere obtener.
	 * @return La solicitud encontrada o null en caso de no haber dicha
	 *         solicitud. (Esto no debería pasar)
	 */
	public SolicitudDomain findByProductId(String productId);

	/**
	 * Método encargado de eliminar la solicitud asociada a un producto
	 * 
	 * @param productId
	 *            Id del producto cuya solicitud se queire borrar.
	 */
	public void deleteByProductId(String productId);
}
