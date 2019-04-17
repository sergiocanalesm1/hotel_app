package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Habitacion {
	private String numero;
	
	private TipoHabitacion tipoHabitacion;
	
	private Timestamp inicioMantenimiento;
	
	private Timestamp finMantenimiento;



	public Habitacion(String numero, TipoHabitacion tipoHabitacion, Timestamp inicioMantenimiento,
			Timestamp finMantenimiento) {
		this.numero = numero;
		this.tipoHabitacion = tipoHabitacion;
		this.inicioMantenimiento = inicioMantenimiento;
		this.finMantenimiento = finMantenimiento;
	}
	public Habitacion() {
		
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}
	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
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

}
