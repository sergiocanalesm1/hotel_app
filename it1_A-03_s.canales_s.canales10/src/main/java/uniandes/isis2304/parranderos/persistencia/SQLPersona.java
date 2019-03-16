/**
 * 
 */
package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


/**
 * NUMERO DEL ARREGLO 1
 *
 */
public class SQLPersona {
	
	/**
	 * tipo de consulta a sql
	 */
	private final static String SQL = Persistencia.SQL;
	
	/**
	 * manejador de persistencia
	 */
	private Persistencia p;


	
	/**
	 * crea un objeto sqlPersona
	 * @param p persistencia
	 */
	public SQLPersona(Persistencia p){
		this.p = p;
	}
	
	/**
	 * agrega una persona a la tabla
	 * @param pm
	 * @param idPersona
	 * @param nombre
	 * @param edad
	 * @param telefono
	 * @return
	 */
	public String adicionarPersona(PersistenceManager pm, String idPersona, String nombre, int edad, String telefono){
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + p.darTablaPersona() + "(idPersona, nombre, edad, telefono) values(?,?,?,?) " );
		q.setParameters(idPersona,nombre,edad,telefono);
		return (String) q.executeUnique();
	}
	
	public String eliminarPersonaPorId(PersistenceManager pm, String id){
		
		Query q = pm.newQuery(SQL, "DELETE FROM " + p.darTablaPersona() + " WHERE idPersona = ?");
		q.setParameters(id);
		return (String) q.executeUnique();
	}
	public Persona darPersonarPorId (PersistenceManager pm, String id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaPersona() + " WHERE idPersona = ?");
		q.setResultClass(Persona.class);
		q.setParameters(id);
		return (Persona) q.executeUnique();
	}

	
	public List<Persona> darPersonaPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaPersona () + " WHERE nombre = ?");
		q.setResultClass(Persona.class);
		q.setParameters(nombre);
		return (List<Persona>) q.executeList();
	}
	public List<Persona> darPersonas (PersistenceManager pm) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaPersona () );
		q.setResultClass(Persona.class);
		return (List<Persona>) q.executeList();
	}

}
