package uniandes.isis2304.parranderos.negocio;

public class Servicio 
{
	private long id;
	
	private String nombre;
	
	private String descripcion;
	
	private int costo;
	
	
	public Servicio(long id, String cantidad, String descripcion, int costo) {
		this.id = id;
		this.nombre = cantidad;
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
