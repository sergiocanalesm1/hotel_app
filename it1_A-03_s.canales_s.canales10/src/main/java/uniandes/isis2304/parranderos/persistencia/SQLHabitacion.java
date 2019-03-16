package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * NUMERO DEL ARREGLO 5
 *
 */
public class SQLHabitacion {
	
	/**
	 * tipo de consulta a sql
	 */
	private final static String SQL = Persistencia.SQL;
	
	/**
	 * manejador de persistencia
	 */
	private Persistencia p;


	
	/**
	 * crea un objeto sqlHabitacion
	 * @param p persistencia
	 */
	public SQLHabitacion(Persistencia p){
		this.p = p;
	}
	
	/**
	 * agrega una persona a la tabla
	 * @param pm
	 * @param idHabitacion
	 * @param nombre
	 * @param edad
	 * @param telefono
	 * @return
	 */
	public String adicionarHabitacion(PersistenceManager pm, String idHabitacion, String numeroHabitacion, int capacidad,double costoPorNoche, String tipoHabitacion){
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + p.darTablaHabitacion() + "(idHabitacion, numeroHabitacion, capacidad,costoPorNoche,  tipoHabitacion) values(?,?,?,?,?) " );
		q.setParameters(idHabitacion, numeroHabitacion, capacidad,costoPorNoche,  tipoHabitacion);
		return (String) q.executeUnique();
	}
	
	public String eliminarHabitacionPorId(PersistenceManager pm, String id){
		
		Query q = pm.newQuery(SQL, "DELETE FROM " + p.darTablaHabitacion() + " WHERE idHabitacion = ?");
		q.setParameters(id);
		return (String) q.executeUnique();
	}
	public Habitacion darHabitacionrPorId (PersistenceManager pm, String id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaHabitacion() + " WHERE idHabitacion = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(id);
		return (Habitacion) q.executeUnique();
	}

	
	public List<Habitacion> darHabitacionPorNumero (PersistenceManager pm, String numeroHabitacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaHabitacion () + " WHERE numeroHabitacion = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(numeroHabitacion);
		return (List<Habitacion>) q.executeList();
	}
	public List<Habitacion> darHabitacions (PersistenceManager pm) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaHabitacion () );
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}

}
