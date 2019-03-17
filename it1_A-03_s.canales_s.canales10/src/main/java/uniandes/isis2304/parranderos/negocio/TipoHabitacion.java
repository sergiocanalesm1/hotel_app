package uniandes.isis2304.parranderos.negocio;

public class TipoHabitacion {
	
	private String nombre;
	
	private int capacidad;
	
	private double costoPorNoche;
	
	private int cantidadDisponible;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public double getCostoPorNoche() {
		return costoPorNoche;
	}

	public void setCostoPorNoche(double costoPorNoche) {
		this.costoPorNoche = costoPorNoche;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public TipoHabitacion(String nombre, int capacidad, double costoPorNoche, int cantidadDisponible) {
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.costoPorNoche = costoPorNoche;
		this.cantidadDisponible = cantidadDisponible;
	}
	
	
}
