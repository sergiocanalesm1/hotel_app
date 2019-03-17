package uniandes.isis2304.parranderos.negocio;

public class Producto {

	private long id;
	
	private String nombre;
	
	private int duracion;
	
	private long idServicio;

	public Producto(long id, String nombre, int duracion, long idServicio) {
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.idServicio = idServicio;
	}
	public Producto()
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
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}
	
	
	
}
