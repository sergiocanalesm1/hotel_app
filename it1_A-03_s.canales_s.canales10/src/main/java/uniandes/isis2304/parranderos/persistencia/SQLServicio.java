package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServicio() + "(id, nombre, descripcion, costo) values (?, ?, ?, ?)");
        q.setParameters(idServicio, nombre, descripcion, costo);
        return (long) q.executeUnique();
	}

}
