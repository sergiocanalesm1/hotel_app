package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


/**
 * NUMERO DEL ARREGLO 4
 *
 */
public class SQLServicio {
	
	/**
	 * tipo de consulta a sql
	 */
	private final static String SQL = Persistencia.SQL;
	
	/**
	 * manejador de persistencia
	 */
	private Persistencia p;


	
	/**
	 * crea un objeto sqlServicio
	 * @param p persistencia
	 */
	public SQLServicio(Persistencia p){
		this.p = p;
	}
	
	/**
	 * agrega una persona a la tabla
	 * @param pm
	 * @param idServicio
	 * @param nombre
	 * @param edad
	 * @param telefono
	 * @return
	 */
	public String adicionarServicio(PersistenceManager pm, String idServicio, char esComplementario){
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + p.darTablaServicio() + "(idServicio, esComplementario) values(?,?) " );
		q.setParameters(idServicio,esComplementario);
		return (String) q.executeUnique();
	}
	
	public String eliminarServicioPorId(PersistenceManager pm, String id){
		
		Query q = pm.newQuery(SQL, "DELETE FROM " + p.darTablaServicio() + " WHERE idServicio = ?");
		q.setParameters(id);
		return (String) q.executeUnique();
	}
	public Servicio darServiciorPorId (PersistenceManager pm, String id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaServicio() + " WHERE idServicio = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(id);
		return (Servicio) q.executeUnique();
	}

	
	
	public List<Servicio> darServicios (PersistenceManager pm) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaServicio () );
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}

}