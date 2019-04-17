package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Habitacion {
	private String numeroHabitacion;
	
	private TipoHabitacion tipoHabitacion;
	
	private Timestamp inicioMantenimiento;
	
	private Timestamp finMantenimiento;



	public Habitacion(String numeroHabitacion, TipoHabitacion tipoHabitacion, Timestamp inicioMantenimiento,
			Timestamp finMantenimiento) {
		this.numeroHabitacion = numeroHabitacion;
		this.tipoHabitacion = tipoHabitacion;
		this.inicioMantenimiento = inicioMantenimiento;
		this.finMantenimiento = finMantenimiento;
	}


	public Timestamp getInicioMantenimiento() {
		return inicioMantenimiento;
	}


	public void setInicioMantenimiento(Timestamp inicioMantenimiento) {
		this.inicioMantenimiento = inicioMantenimiento;
	}


	public Timestamp getFinMantenimiento() {
		return finMantenimiento;
	}


	public void setFinMantenimiento(Timestamp finMantenimiento) {
		this.finMantenimiento = finMantenimiento;
	}


	public String getNumeroHabitacion() {
		return numeroHabitacion;
	}

	public void setNumeroHabitacion(String numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}
	
}
