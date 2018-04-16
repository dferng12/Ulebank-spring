/**
 * 
 */
package es.unileon.ulebankoffice.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Razvan Raducu
 *
 */
public class SolicitudFinancialAdvisor {
	
	@NotBlank
	private String textoOferta;

	private String urlOferta;
	
	@NotBlank
	private String asuntoOferta;
	
    private List<MultipartFile> files;
    
    public List<MultipartFile> getFiles() {
        return this.files;
    }
 
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
	
	public String getTextoOferta() {
		return textoOferta;
	}
	
	public void setTextoOferta(String textoOferta) {
		this.textoOferta = textoOferta;
	}
	
	public String getUrlOferta() {
		return urlOferta;
	}
	public void setUrlOferta(String urlOferta) {
		this.urlOferta = urlOferta;
	}
	public String getAsuntoOferta() {
		return asuntoOferta;
	}
	public void setAsuntoOferta(String asuntoOferta) {
		this.asuntoOferta = asuntoOferta;
	}

	
}
