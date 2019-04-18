package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Servicio 
{
	private long id;
	
	private String nombre;
	
	private String descripcion;
	
	private int costo;
	
	private Timestamp inicioMantenimiento;
	
	private Timestamp finMantenimiento;
	

	public Servicio(long id, String nombre, String descripcion, int costo, Timestamp inicioMantenimiento,
			Timestamp finMantenimiento) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
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
	public Servicio()
	{
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCosto() {
		return costo;
	}
	public void setCosto(int costo) {
		this.costo = costo;
	}

	
	
}
