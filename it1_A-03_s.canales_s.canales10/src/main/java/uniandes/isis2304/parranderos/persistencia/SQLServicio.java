package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Servicio;

public class SQLServicio {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = Persistencia.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private Persistencia pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLServicio (Persistencia pp)
	{
		this.pp = pp;
	}

	public long adicionarServicio(PersistenceManager pm, long idServicio, String nombre,
			String descripcion, int costo) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServicio() + " (id, nombre, descripcion, costo) values (?, ?, ?, ?)");
        q.setParameters(idServicio, nombre, descripcion, costo);
        return (long) q.executeUnique();
	}

	public Servicio getServicio(PersistenceManager pm, String id) {
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio() + " WHERE id = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(Long.parseLong(id));
		return (Servicio) q.executeUnique();
	}

	public List<Servicio> getServicios(PersistenceManager pm, String nombre) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio() + " WHERE nombre = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(nombre);
		return (List<Servicio>) q.executeList();
	}

	public long registrarMantenimiento(PersistenceManager pm, String idServicio, String fechaInicioMantenimiento, String fechaFinMantenimiento) {
			Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaServicio() + " SET inicioMantenimiento = to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS') , finMantenimiento = to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS') WHERE id = ? ");
			q.setResultClass(Long.class);
		    q.setParameters(fechaInicioMantenimiento, fechaFinMantenimiento, Integer.parseInt(idServicio));
		    return (long) q.executeUnique();
		

	}

}
