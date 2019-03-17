package uniandes.isis2304.parranderos.negocio;

public class PlanConsumo 
{
	private String nombre;
	
	private int porcentajeDeDescuento;
	
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPorcentajeDeDescuento() {
		return porcentajeDeDescuento;
	}

	public void setPorcentajeDeDescuento(int porcentajeDeDescuento) {
		this.porcentajeDeDescuento = porcentajeDeDescuento;
	}

	
	public PlanConsumo(String nombre, int porcentajeDeDescuento, String descripcion) {
		this.nombre = nombre;
		this.porcentajeDeDescuento = porcentajeDeDescuento;
		this.descripcion = descripcion;
	}

	public PlanConsumo()
	{
		
	}
	
	
}
