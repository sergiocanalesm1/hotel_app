package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLHabitacion {

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
	public SQLHabitacion(Persistencia p){
		
		persistencia = p;
	}

	public String adicionarHabitacion(PersistenceManager pm, String numero, String tipoHabitacion) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaHabitacion() + "(numero, tipoHabitacion) values (?,?)");
		q.setParameters(numero, tipoHabitacion);
		System.out.println("sqlasdf");
		return q.executeUnique() + "";
	}

}
