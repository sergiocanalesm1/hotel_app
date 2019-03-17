package uniandes.isis2304.parranderos.negocio;

public class PlanConsumo 
{
	private String nombre;
	
	private int porcentajeDescuento;
	
	private String descripcion;

	public PlanConsumo()
	{
		
	}
	public PlanConsumo(String nombre, int porcentajeDescuento, String descripcion) {
		this.nombre = nombre;
		this.porcentajeDescuento = porcentajeDescuento;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(int porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	
	
}
