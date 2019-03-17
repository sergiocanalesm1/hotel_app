package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Habitacion;
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

	public String adicionarUsuario(PersistenceManager pm, String nombre,  String edad,  String tel,  String tipoDoc, String numeroDoc,
			 String correo,  Timestamp fechaLlegada,  Timestamp fechaSalida,  String cargo,  Habitacion hab) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaUsuario() + "(nombre, edad, telefono,tipoDocumento, numerodocumento ,correo, fechaLlegada , fechaSalida, rol,numerohabitacion) values (?,?,?,?,?,?,?,?,?,?)");
		q.setParameters(nombre, edad, tel,tipoDoc, numeroDoc ,correo, fechaLlegada , fechaSalida, cargo,hab);
		
		return q.executeUnique() + "";
	}

}
