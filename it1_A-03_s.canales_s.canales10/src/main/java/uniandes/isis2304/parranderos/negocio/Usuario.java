package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Usuario 
{
	
	private String nombre;
	
	private String edad;
	
	private String telefono;
	
	private String tipoDocumento;
	
	private String numeroDocumento;
	
	private String correo;
	
	private Timestamp fechaLlegada;
	
	private Timestamp fechaSalida;
	
	
	private String rol;
	
	private String numeroHabitacion;



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Timestamp getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Timestamp fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public Timestamp getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Timestamp fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNumeroHabitacion() {
		return numeroHabitacion;
	}

	public void setNumeroHabitacion(String numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	public Usuario( String nombre, String edad, String telefono, String tipoDocumento,
			String numeroDocumento, String correo, Timestamp fechaLlegada, Timestamp fechaSalida, String rol,
			String numeroHabitacion) {
		this.nombre = nombre;
		this.edad = edad;
		this.telefono = telefono;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.correo = correo;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
		this.rol = rol;
		this.numeroHabitacion = numeroHabitacion;
	}
	public Usuario()
	{
		
	}

	
	
	
}
