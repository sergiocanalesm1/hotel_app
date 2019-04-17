package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Habitacion;

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
		return q.executeUnique() + "";
	}

	public long darTotalHabitaciones(PersistenceManager pm, String tipoHabitacion)
	{
		Query q = pm.newQuery(SQL, "SELECT COUNT(*) FROM " + persistencia.darTablaHabitacion() + " WHERE tipohabitacion = ?");
		q.setParameters(tipoHabitacion);
		return ((BigDecimal) q.executeUnique()).longValue();
	}

	public Habitacion getHabitacion(PersistenceManager pm, String numeroHab) {

		Query q = pm.newQuery(SQL, "SELECT * FROM " + persistencia.darTablaHabitacion() + "WHERE numero = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(numeroHab);
		return (Habitacion) q.executeUnique();

	}
	public Integer getCantidadDeHabitacionPorTipo(PersistenceManager pm, String tipo) {

		Query q = pm.newQuery(SQL, "SELECT COUNT(*) FROM "  + persistencia.darTablaHabitacion() + " WHERE tipohabitacion = ?");
		q.setResultClass(Integer.class);
		q.setParameters(tipo);
		return (Integer) q.executeUnique();
	}

	public String registrarMantenimiento(PersistenceManager pm, String numeroHabitacion, Timestamp fechaInic, Timestamp fechaFin) {
			Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaHabitacion () + " SET inicioMantenimiento = ? , finMantenimiento = ? WHERE numero = ?");
			q.setResultClass(String.class);
		    q.setParameters(fechaInic, fechaFin, numeroHabitacion);
		    return (String) q.executeUnique();
		

	}

}
