package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLTipoHabitacion {

	/**
	 * tipo de consulta a sql
	 */
	private final static String SQL = Persistencia.SQL;
	
	/**
	 * manejador de persistencia
	 */
	private Persistencia persistencia;
	
	/**
	 * constructor
	 */
	public SQLTipoHabitacion(Persistencia p){
		
		persistencia = p;
	}
	public String adicionarTipoHabitacion(PersistenceManager pm, String nombre, Integer capacidad, Double costoPorNoche,
			Integer cantidadDisponible) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaTipoHabitacion() + "(nombre, capacidad, costoPorNoche ,cantidadDisponible) values (?,?,?,?)");
		q.setParameters(nombre, capacidad, costoPorNoche ,cantidadDisponible);
		
		return q.executeUnique() + "";
	}
	
	public String cambiarCantidadDisponible(PersistenceManager pm, String nombre, Integer nuevaCapacidad){
		
		 Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaTipoHabitacion () + " SET capacidad = ? WHERE nombre = ?");
	     q.setParameters(nombre, nuevaCapacidad);
	     return q.executeUnique() +""; 
	}

}
