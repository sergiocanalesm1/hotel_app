package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.Producto;

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
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaProducto() + " (id, nombre, duracion,idServicio) values (?,?,?,?)");
		q.setParameters(id, nombre,duracion,idServicio);
		return (long) q.executeUnique() ;
		
	}

	public Producto getProducto(PersistenceManager pm, String id) {
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + persistencia.darTablaProducto() + " WHERE id = ?");
		q.setResultClass(Producto.class);
		q.setParameters(id);
		return (Producto) q.executeUnique();
	}

	

	public long cancelarReservasConvencion(PersistenceManager pm, String idOrganizador) throws Exception
	{
		Query q = pm.newQuery(SQL, "DELETE " + persistencia.darTablaProducto() + " WHERE nombre like ?");//no tiene set result class
		q.setParameters(idOrganizador);
		return (long) q.executeUnique();
		
	}

	public long cancelarMantenimiento(PersistenceManager pm, String idMantenimiento) {
		Query q = pm.newQuery(SQL, "DELETE " + persistencia.darTablaProducto() + " WHERE idServicio = ?");//no tiene set result class
		q.setParameters(idMantenimiento);
		return (long) q.executeUnique();
	}

}
