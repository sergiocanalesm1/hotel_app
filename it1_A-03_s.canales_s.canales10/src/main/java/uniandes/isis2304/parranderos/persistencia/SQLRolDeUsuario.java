package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.RolDeUsuario;

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
	
	public String adicionarRolDeUsuario(PersistenceManager pm, String cargo, String descripcion){
		
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaUsuario() + "(cargo, descripcion) values (?,?)");
		q.setParameters(cargo, descripcion);
		String rol = q.executeUnique() + "";
		return rol;
	}

	public RolDeUsuario getRolDeUsuario(PersistenceManager pm, String cargo) {
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + persistencia.darTablaRolesDeUsuario() + " WHERE cargo = ?");
		q.setResultClass(RolDeUsuario.class);
		q.setParameters(cargo);
		return (RolDeUsuario) q.executeUnique();	
		}

}
