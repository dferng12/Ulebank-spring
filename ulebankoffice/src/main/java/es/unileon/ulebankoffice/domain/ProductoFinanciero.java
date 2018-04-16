package es.unileon.ulebankoffice.domain;

import java.util.Date;

/**
 * @author Razvan Raducu
 *
 */
public interface ProductoFinanciero {
	public Date getFechaSolicitud();
	public Date getFechaResolucion();
	public Date getFechaFinalizacion();
	public String getEstado();
	public Handler getDni();
	
}
