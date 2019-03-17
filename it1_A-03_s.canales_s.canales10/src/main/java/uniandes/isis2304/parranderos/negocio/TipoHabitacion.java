package uniandes.isis2304.parranderos.negocio;

public class TipoHabitacion {
	
	private String nombre;
	
	private Integer capacidad;
	
	private Integer costoPorNoche;
	
	private Integer cantidadDisponible;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getCostoPorNoche() {
		return costoPorNoche;
	}

	public void setCostoPorNoche(Integer costoPorNoche) {
		this.costoPorNoche = costoPorNoche;
	}

	public Integer getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(Integer cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public TipoHabitacion(String nombre, Integer capacidad, Integer costoPorNoche, Integer cantidadDisponible) {
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.costoPorNoche = costoPorNoche;
		this.cantidadDisponible = cantidadDisponible;
	}
	
	
}
