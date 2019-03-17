package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLProducto {
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
	public SQLProducto(Persistencia p){
		
		persistencia = p;
	}

	public long adicionarProducto(PersistenceManager pm, long id, String nombre, String duracion,
			String idServicio) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaProducto() + "(id, nombre, duracion,idServicio) values (?,?,?,?)");
		q.setParameters(id, nombre,duracion,idServicio);
		return (long) q.executeUnique() ;
		
	}

}
