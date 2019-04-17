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
	public long adicionarConsumo(PersistenceManager pm, long id, String valor, Timestamp fechaRegistro,	String numeroHabitacionACargar, String idServicioACargar, String idConvencion) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaConsumo() + " (id, valor, fechaRegistro,numeroHabitacionACargar, idserviciocargado, idConvencion) values (?,?,?,?,?,?)");
		q.setParameters(id, valor, fechaRegistro,numeroHabitacionACargar, idServicioACargar,idConvencion);
		return (long) q.executeUnique() ;
	}
	public long finDeConvencion(PersistenceManager pm, String idOrganizador) {
		Query q = pm.newQuery(SQL ," SELECT SUM(con.valor) "
				+ "FROM CONSUMO con, USUARIO usu "
				+ "WHERE usu.numeroDocumento = ? AND con.idConvencion = usu.numeroDocumento " );
		q.setResultClass(Long.class);
		q.setParameters(idOrganizador);
		return (long) q.executeUnique();
	}

}
