package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Reserva {
	private long id;
	
	private String metodoDePago;
	
	private int cantidadPersonas;
	
	private Timestamp fechaComienzo;
	
	private Timestamp fechaFin;
	
	private String tipoHabitacion;
	
	private String planConsumo;
	
	private String idUsuario;
	
	private long idProducto;

	
	
	



	public Reserva(long id, String metodoDePago, int cantidadPersonas, Timestamp fechaComienzo, Timestamp fechaFin,
			String tipoHabitacion, String planConsumo, String idUsuario, long idProducto) {
		this.id = id;
		this.metodoDePago = metodoDePago;
		this.cantidadPersonas = cantidadPersonas;
		this.fechaComienzo = fechaComienzo;
		this.fechaFin = fechaFin;
		this.tipoHabitacion = tipoHabitacion;
		this.planConsumo = planConsumo;
		this.idUsuario = idUsuario;
		this.idProducto = idProducto;
	}







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







	public void setCantidadPersonas(int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}







	public Timestamp getFechaComienzo() {
		return fechaComienzo;
	}







	public void setFechaComienzo(Timestamp fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}







	public Timestamp getFechaFin() {
		return fechaFin;
	}







	public void setFechaFin(Timestamp fechaFin) {
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







	public long getIdProducto() {
		return idProducto;
	}







	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}







	public Reserva()
	{
		
	}
	
	
}
