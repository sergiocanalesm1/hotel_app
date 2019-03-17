package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLRolDeUsuario {
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
	public SQLRolDeUsuario(Persistencia p){
		
		persistencia = p;
	}
	
	public long adicionarRolDeUsuario(PersistenceManager pm, String cargo, String descripcion ){
		System.out.println("llega a sqlRolDeUsuario");
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaRolesDeUsuario() + "(cargo, descripcion) values (?,?)");
		q.setParameters(cargo, descripcion);
		
		return (long)q.executeUnique();
	}

}
