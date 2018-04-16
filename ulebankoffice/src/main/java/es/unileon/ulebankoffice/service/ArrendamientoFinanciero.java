package es.unileon.ulebankoffice.service;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ArrendamientoFinanciero {
	
	@NotNull @Min(1)
	private double valorBien;
	
	@NotNull @Min(1)
	private int duracionContrato;
	
	private int fraccionamientoPagoCuota;
	
	@NotNull @Min(0)
	private double tipoInteres;

	public double getValorBien() {
		return valorBien;
	}

	public void setValorBien(double valorBien) {
		this.valorBien = valorBien;
	}

	public int getDuracionContrato() {
		return duracionContrato;
	}

	public void setDuracionContrato(int duracionContrato) {
		this.duracionContrato = duracionContrato;
	}

	public int getFraccionamientoPagoCuota() {
		return fraccionamientoPagoCuota;
	}

	public void setFraccionamientoPagoCuota(int fraccionamientoPagoCuota) {
		this.fraccionamientoPagoCuota = fraccionamientoPagoCuota;
	}

	public double getTipoInteres() {
		return tipoInteres;
	}

	public void setTipoInteres(double tipoInteres) {
		this.tipoInteres = tipoInteres;
	}
}
