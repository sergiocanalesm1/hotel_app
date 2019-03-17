package uniandes.isis2304.parranderos.negocio;

public class Habitacion {
	private String numeroHabitacion;
	
	private TipoHabitacion tipoHabitacion;

	public Habitacion(String numeroHabitacion, TipoHabitacion tipoHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
		this.tipoHabitacion = tipoHabitacion;
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
