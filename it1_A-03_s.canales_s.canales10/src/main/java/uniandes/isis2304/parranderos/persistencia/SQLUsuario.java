package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.Usuario;

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
			 String correo,  Timestamp fechaLlegada,  Timestamp fechaSalida,  String cargo,  Habitacion hab, String idConvencion) {
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + persistencia.darTablaUsuario() + "(nombre, edad, telefono,tipoDocumento, numerodocumento ,correo, fechaLlegada , fechaSalida, rol,numerohabitacion, idConvencion) values (?,?,?,?,?,?,?,?,?,?,?)");
		q.setParameters(nombre, edad, tel,tipoDoc, numeroDoc ,correo, fechaLlegada , fechaSalida, cargo,hab, idConvencion);
		
		return q.executeUnique() + "";
	}

	public Usuario getUsuario(PersistenceManager pm, String id) {
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + persistencia.darTablaUsuario() + " WHERE numeroDocumento = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(id);
		return (Usuario) q.executeUnique();	
		}


		public String updateReserva(PersistenceManager pm,String idUsuario, Timestamp llegada) {
			
			Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaUsuario () + " SET fechaLlegada = ? WHERE idUsuario = ?");
			q.setResultClass(String.class);
			q.setParameters(llegada, idUsuario );
		    return (String) q.executeUnique();
		}

		public String updateReservaBySalida(PersistenceManager pm, String idUsuario, Timestamp fechaSalida) {
			Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaUsuario () + " SET fechafin = ? WHERE idUsuario = ?");
			q.setResultClass(String.class);
		    q.setParameters(fechaSalida, idUsuario );
		     
		     return (String) q.executeUnique();
		}

		public String getRolDeUsuarioById(PersistenceManager pm, String id) {
			Query q = pm.newQuery(SQL, "SELECT rol FROM " + persistencia.darTablaUsuario() + " WHERE numeroDocumento = ?");
			q.setResultClass(String.class);
			q.setParameters(id);
			return (String) q.executeUnique();
		}
	
	

}
