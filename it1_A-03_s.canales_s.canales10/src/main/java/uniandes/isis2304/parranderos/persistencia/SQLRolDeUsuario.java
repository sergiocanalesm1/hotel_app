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
	
	public String adicionarRolDeUsuario(PersistenceManager pm, String cargo, String descripcion ){
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaUsuario() + "(cargo, descripcion)");
		q.setParameters(cargo, descripcion);
		return (String) q.executeUnique();
	}

}
