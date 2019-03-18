package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Reserva;

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
			int numeroPersonas, Timestamp fechaComienzo, Timestamp fechaFin, String tipoHabitacion, String planConsumo, String idUsuario, String idProducto) {
		Query q = null;
		if( idProducto.equals("-1") )
		{
			q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaReserva() + "(id, metodoDePago, cantidadpersonas, fechaComienzo, fechaFin, tipohabitacion, planConsumo, idUsuario) values (?,?,?,?,?,?,?,?)");
			q.setParameters(id, metodoDePago, numeroPersonas, fechaComienzo, fechaFin, tipoHabitacion, planConsumo, idUsuario);
		}
		else
		{
			q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaReserva() + "(id, metodoDePago, cantidadpersonas, fechaComienzo, fechaFin, tipohabitacion, planConsumo, idUsuario, idProducto) values (?,?,?,?,?,?,?,?,?)");
			q.setParameters(id, metodoDePago, numeroPersonas, fechaComienzo, fechaFin, tipoHabitacion, planConsumo, idUsuario, idProducto);
		}
			
		
		return (long) q.executeUnique();
	}

	public long darReservasHabitacion(PersistenceManager pm, String valor, Timestamp fechaCo, Timestamp fechaFi) 
	{
		String comparacion = "tipohabitacion";
		if( !valor.equals("-1"))
		{
			comparacion = "idProducto";
		}
			
		String sql1 = "AND (fechacomienzo >=  ? AND fechacomienzo < ?) or ( fechafin > ? and fechaFin <= ?) or (fechacomienzo <= ? AND fechafin >= ?)";
		Query q = pm.newQuery(SQL, "SELECT COUNT(*) FROM " + persistencia.darTablaReserva() + " WHERE "+comparacion+" = ? "+sql1);
		
		q.setParameters(valor, fechaCo, fechaFi, fechaCo, fechaFi, fechaCo, fechaFi);
		return ((BigDecimal) q.executeUnique()).longValue();
	}

	public Reserva getReservado(PersistenceManager pm,String idUsuario, Timestamp llegada) {
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + persistencia.darTablaReserva() + " WHERE idUsuario = ? AND fechaComienzo = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idUsuario, llegada);
		return (Reserva) q.executeUnique();
	}

	public void updateReserva(PersistenceManager pm,String idUsuario, Timestamp llegada) {
		
		Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaReserva () + " SET fechaComienzo = ? WHERE idUsuario = ?");
	     q.setParameters(llegada, idUsuario );
	     q.executeUnique();
	}

	
}
