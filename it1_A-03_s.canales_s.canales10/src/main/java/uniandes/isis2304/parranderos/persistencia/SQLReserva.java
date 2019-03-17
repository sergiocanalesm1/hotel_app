package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
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
			int numeroPersonas, Date fechaComienzo, Date fechaFin, String tipoHabitacion, String planConsumo, String idUsuario) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaReserva() + "(id, metodoDePago, cantidadpersonas, fechaComienzo, fechaFin, tipohabitacion, planConsumo, idUsuario) values (?,?,?,?,?,?,?,?)");
		q.setParameters(id, metodoDePago, numeroPersonas, fechaComienzo, fechaFin, tipoHabitacion, planConsumo, idUsuario);
		return (long) q.executeUnique();
	}

	public long darReservasHabitacion(PersistenceManager pm, String tipoHabitacion, Date fechaCo,
			Date fechaFi) 
	{
		String sql1 = "AND (fechacomienzo >=  ? AND fechacomienzo < ?) or ( fechafin > ? and fechaFin <= ?) or (fechacomienzo <= ? AND fechafin >= ?)";
		Query q = pm.newQuery(SQL, "SELECT COUNT(*) FROM " + persistencia.darTablaReserva() + " WHERE tipohabitacion = ? "+sql1);
		
		q.setParameters(tipoHabitacion, fechaCo, fechaFi, fechaCo, fechaFi, fechaCo, fechaFi);
		return ((BigDecimal) q.executeUnique()).longValue();
	}

}
