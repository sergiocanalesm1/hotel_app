package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.PlanConsumo;

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

	public String adicionarPlanConsumo(PersistenceManager pm, String nombre, int porcentajeDescuento, String des) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + p.darTablaPlanConsumo() + "(nombre, porcentajeDescuento, descripcion) values (?, ?, ?)");
        q.setParameters( nombre, porcentajeDescuento, des);
        return  q.executeUnique() + "";
	}

	public PlanConsumo getPlanConsumo(PersistenceManager pm, String nombre) {
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + p.darTablaPlanConsumo() + " WHERE nombre = ?");
		q.setResultClass(PlanConsumo.class);
		q.setParameters(nombre);
		System.out.println("ACA LLEGA SQL");
		return (PlanConsumo) q.executeUnique();
	}

}
