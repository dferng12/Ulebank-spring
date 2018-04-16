/**
 * 
 */
package es.unileon.ulebankoffice.service;

import java.util.List;

/**
 * @author Razvan Raducu
 *
 */
public class TAEVariosIntereses {
	
	private List<InteresVariable> intereses;
	
	private int periodo;
	
	public List<InteresVariable> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<InteresVariable> intereses) {
		this.intereses = intereses;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	
	
}
