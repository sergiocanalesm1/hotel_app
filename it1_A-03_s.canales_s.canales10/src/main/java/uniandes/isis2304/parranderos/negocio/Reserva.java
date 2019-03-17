package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

public class Reserva {
	private int id;
	
	private String metodoDePago;
	
	private int numeroPersonas;
	
	private Date fechaComienzo;
	
	private Date fechaFin;
	
	private TipoHabitacion tipoHabitacion;
	
	private PlanConsumo planConsumo;
	
	private Usuario idUsuario;

	
	public Reserva(int id, String metodoDePago, int numeroPersonas, Date fechaComienzo, Date fechaFin,
			TipoHabitacion tipoHabitacion, PlanConsumo planConsumo, Usuario idUsuario) {
		this.id = id;
		this.metodoDePago = metodoDePago;
		this.numeroPersonas = numeroPersonas;
		this.fechaComienzo = fechaComienzo;
		this.fechaFin = fechaFin;
		this.tipoHabitacion = tipoHabitacion;
		this.planConsumo = planConsumo;
		this.idUsuario = idUsuario;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMetodoDePago() {
		return metodoDePago;
	}


	public void setMetodoDePago(String metodoDePago) {
		this.metodoDePago = metodoDePago;
	}


	public int getNumeroPersonas() {
		return numeroPersonas;
	}


	public void setNumeroPersonas(int numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
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


	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}


	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}


	public PlanConsumo getPlanConsumo() {
		return planConsumo;
	}


	public void setPlanConsumo(PlanConsumo planConsumo) {
		this.planConsumo = planConsumo;
	}


	public Usuario getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}


	public Reserva()
	{
		
	}
	
	
}
