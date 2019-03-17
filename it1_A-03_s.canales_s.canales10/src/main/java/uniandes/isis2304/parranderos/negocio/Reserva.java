package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

public class Reserva {
	private long id;
	
	private String metodoDePago;
	
	private int cantidadPersonas;
	
	private Date fechaComienzo;
	
	private Date fechaFin;
	
	private String tipoHabitacion;
	
	private String planConsumo;
	
	private String idUsuario;

	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getMetodoDePago() {
		return metodoDePago;
	}



	public void setMetodoDePago(String metodoDePago) {
		this.metodoDePago = metodoDePago;
	}



	public int getCantidadPersonas() {
		return cantidadPersonas;
	}



	public void setCantidadPersonas(int cantidadpersonas) {
		this.cantidadPersonas = cantidadpersonas;
	}



	public Date getFechaComienzo() {
		return fechaComienzo;
	}



	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}



	public Date getFechaFin() {
		return fechaFin;
	}



	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}



	public String getTipoHabitacion() {
		return tipoHabitacion;
	}



	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}



	public String getPlanConsumo() {
		return planConsumo;
	}



	public void setPlanConsumo(String planConsumo) {
		this.planConsumo = planConsumo;
	}



	public String getIdUsuario() {
		return idUsuario;
	}



	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}



	public Reserva(long id, String metodoDePago, int cantidadpersonas, Date fechaComienzo, Date fechaFin,
			String tipoHabitacion, String planConsumo, String idUsuario) {
		this.id = id;
		this.metodoDePago = metodoDePago;
		this.cantidadPersonas = cantidadpersonas;
		this.fechaComienzo = fechaComienzo;
		this.fechaFin = fechaFin;
		this.tipoHabitacion = tipoHabitacion;
		this.planConsumo = planConsumo;
		this.idUsuario = idUsuario;
	}



	public Reserva()
	{
		
	}
	
	
}
