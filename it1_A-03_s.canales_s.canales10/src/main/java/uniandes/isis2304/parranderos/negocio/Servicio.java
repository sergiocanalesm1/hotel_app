package uniandes.isis2304.parranderos.negocio;

public class Servicio 
{
	private long id;
	
	private String cantidad;
	
	private String descripcion;
	
	private int costo;

	
	
	
	public Servicio(long id, String cantidad, String descripcion, int costo) {
		this.id = id;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.costo = costo;
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

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
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
