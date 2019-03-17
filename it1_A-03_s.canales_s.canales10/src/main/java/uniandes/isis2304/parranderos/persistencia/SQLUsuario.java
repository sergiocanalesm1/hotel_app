package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.RolDeUsuario;

public class SQLUsuario {
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
	public SQLUsuario(Persistencia p){
		
		persistencia = p;
	}

	public String adicionarUsuario(PersistenceManager pm, String nombre, String telefono, String tipoDocumento,
			String numeroDocumento, String correo, Timestamp fechaLlegada, Timestamp fechaSalida, String cargo) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaRolesDeUsuario() + "(nombre, telefono, tipoDocumento,numeroDocumento, correo,fechaLlegada, fechaSalida, cargo) values (?,?,?,?,?,?,?,?)");
		q.setParameters(nombre, telefono, tipoDocumento,numeroDocumento, correo,fechaLlegada, fechaSalida,cargo);
		
		return q.executeUnique() + "";
	}

}
