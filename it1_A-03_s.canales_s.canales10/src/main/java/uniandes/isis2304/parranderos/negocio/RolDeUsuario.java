package uniandes.isis2304.parranderos.negocio;

public class RolDeUsuario {
	private String cargo;
	private String descripcion;
	
	
	
	
	
	
	/**
	 * @param cargo
	 * @param descripcion
	 */
	public RolDeUsuario(String cargo, String descripcion) {
		this.cargo = cargo;
		this.descripcion = descripcion;
	}
	public RolDeUsuario() {
		
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
