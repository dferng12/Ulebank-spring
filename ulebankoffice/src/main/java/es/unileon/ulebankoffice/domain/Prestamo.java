package es.unileon.ulebankoffice.domain;

public abstract class Prestamo extends Operacion {
	
	public abstract double getTipoInteres();
	public abstract void setTipoInteres(double tipoInteres);
	
	public abstract double getCapInicial();
	public abstract void setCapInicial(double capInicial);
	
	public abstract int getPeriodos();
	public abstract void setPeriodos(int periodos);
	
	public abstract int getTipoPeriodo();
	public abstract void setTipoPeriodo(int tipoPeriodo);
	
}
