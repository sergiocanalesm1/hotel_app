package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Usuario 
{
	private String id;
	
	private String nombre;
	
	private String edad;
	
	private String telefono;
	
	private String tipoDocumento;
	
	private String numeroDocumento;
	
	private String correo;
	
	private Timestamp fechaLlegada;
	
	private Timestamp fechaSalida;
	
	//private RolDeUsuario cargo;
	
	//private Habitacion numeroHabitacion;

	public Usuario(String id, String nombre, String edad, String telefono, String tipoDocumento, String numeroDocumento,
			String correo, Timestamp fechaLlegada, Timestamp fechaSalida) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.telefono = telefono;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.correo = correo;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
	}
	
	public Usuario()
	{
		
	}
	public String getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEdad() {
		return edad;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public String getCorreo() {
		return correo;
	}
	public Timestamp getFechaLlegada() {
		return fechaLlegada;
	}
	public Timestamp getFechaSalida() {
		return fechaSalida;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public void setFechaLlegada(Timestamp fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}
	public void setFechaSalida(Timestamp fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	
	
	
}
