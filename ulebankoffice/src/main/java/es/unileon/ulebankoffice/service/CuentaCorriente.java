package es.unileon.ulebankoffice.service;

import javax.validation.constraints.NotNull;

/**
 * @author Razvan Raducu
 *
 */
public class CuentaCorriente {
	
	@NotNull
	private Double interesesAcreedores;
	@NotNull
	private Double interesDeudorSobreSaldosNegativos;
	@NotNull
	private Double retencionRendimientosCapital;
	@NotNull
	private Double comisionMantenimiento;
	@NotNull
	private Double comisionDescubierto;
	@NotNull
	private Double minimoComisionDescubierto;
	@NotNull
	private int periodoLiquidacion;
	@NotNull
	private int diasAnuales;
		
	public Double getInteresesAcreedores() {
		return interesesAcreedores;
	}

	public void setInteresesAcreedores(Double interesesAcreedores) {
		this.interesesAcreedores = interesesAcreedores;
	}

	public Double getInteresDeudorSobreSaldosNegativos() {
		return interesDeudorSobreSaldosNegativos;
	}

	public void setInteresDeudorSobreSaldosNegativos(Double interesDeudorSobreSaldosNegativos) {
		this.interesDeudorSobreSaldosNegativos = interesDeudorSobreSaldosNegativos;
	}

	public Double getRetencionRendimientosCapital() {
		return retencionRendimientosCapital;
	}

	public void setRetencionRendimientosCapital(Double retencionRendimientosCapital) {
		this.retencionRendimientosCapital = retencionRendimientosCapital;
	}

	public Double getComisionMantenimiento() {
		return comisionMantenimiento;
	}

	public void setComisionMantenimiento(Double comisionMantenimiento) {
		this.comisionMantenimiento = comisionMantenimiento;
	}

	public Double getComisionDescubierto() {
		return comisionDescubierto;
	}

	public void setComisionDescubierto(Double comisionDescubierto) {
		this.comisionDescubierto = comisionDescubierto;
	}

	public Double getMinimoComisionDescubierto() {
		return minimoComisionDescubierto;
	}

	public void setMinimoComisionDescubierto(Double minimoComisionDescubierto) {
		this.minimoComisionDescubierto = minimoComisionDescubierto;
	}

	public int getPeriodoLiquidacion() {
		return periodoLiquidacion;
	}

	public void setPeriodoLiquidacion(int periodoLiquidacion) {
		this.periodoLiquidacion = periodoLiquidacion;
	}

	public int getDiasAnuales() {
		return diasAnuales;
	}

	public void setDiasAnuales(int diasAnuales) {
		this.diasAnuales = diasAnuales;
	}

}
