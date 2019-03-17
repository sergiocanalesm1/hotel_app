package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLConsumo {

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
	public SQLConsumo(Persistencia p){
		
		persistencia = p;
	}
	public long adicionarConsumo(PersistenceManager pm, long id, String valor, Timestamp fechaRegistro,	String numeroHabitacionACargar, String idServicioACargar) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaConsumo() + "(id, valor, fechaRegistro,numeroHabitacionACargar, idServicioACargar) values (?,?,?,?,?)");
		q.setParameters(id, valor, fechaRegistro,numeroHabitacionACargar, idServicioACargar);
		return (long) q.executeUnique() ;
	}

}
