package es.unileon.ulebankoffice.service;

public class AdminQuestionInfo {
	
	private String pregunta;
	private String ponderacion;
	
	public AdminQuestionInfo(String pregunta, String ponderacion) {
		this.pregunta = pregunta;
		this.ponderacion = ponderacion;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
}
