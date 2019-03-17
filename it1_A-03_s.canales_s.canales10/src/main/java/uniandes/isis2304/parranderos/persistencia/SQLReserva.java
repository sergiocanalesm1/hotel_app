package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLReserva {
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

	public SQLReserva(Persistencia p) {
		persistencia = p;
	}

	public long adicionarReserva(PersistenceManager pm, long id, String metodoDePago,
			int numeroPersonas, Date fechaComienzo, Date fechaFin, String planConsumo, String idUsuario) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaReserva() + "(id, metodoDePago, numeroPersonas, fechaComienzo, fechaFin, planConsumo, idUsuario) values (?,?,?,?,?,?,?)");
		q.setParameters(id, metodoDePago, numeroPersonas, fechaComienzo, fechaFin, planConsumo, idUsuario);
		return (long) q.executeUnique();
	}

}
