package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLPlanConsumo {
	
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
	private Persistencia p;
	public SQLPlanConsumo(Persistencia p) {
		this.p = p;
	}

	public String adicionarPlanConsumo(PersistenceManager pm, String nombre, int porcentajeDescuento) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + p.darTablaPlanConsumo() + "(nombre, porcentajeDescuento) values (?, ?)");
        q.setParameters( nombre, porcentajeDescuento);
        return  q.executeUnique() + "";
	}

}
