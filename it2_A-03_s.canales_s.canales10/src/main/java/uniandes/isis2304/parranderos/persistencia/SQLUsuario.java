package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

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


		public long updateReserva(PersistenceManager pm,String idUsuario, String llegada) {
			
			Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaUsuario () + " SET fechaLlegada = to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS')  WHERE numeroDocumento = ?");
			q.setResultClass(Long.class);
			q.setParameters(llegada, idUsuario );
		    return (long) q.executeUnique();
		}

		public long updateReservaBySalida(PersistenceManager pm, String idUsuario, String fechaSalida) {
			Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaUsuario () + " SET fechaSalida = to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS') , numeroHabitacion = ? WHERE numeroDocumento = ?");
			q.setResultClass(Long.class);
		    q.setParameters(fechaSalida, null, idUsuario );
		     
		     return (long) q.executeUnique();
		}

		public String getRolDeUsuarioById(PersistenceManager pm, String id) {
			Query q = pm.newQuery(SQL, "SELECT rol FROM " + persistencia.darTablaUsuario() + " WHERE numeroDocumento = ?");
			q.setResultClass(String.class);
			q.setParameters(id);
			return (String) q.executeUnique();
		}

		public void asociarHabitacion(PersistenceManager pm, String idUsuario) {
			
			String sql = "UPDATE USUARIO\r\n" + 
					" SET numeroHabitacion = \r\n" + 
					"   (\r\n" + 
					"    SELECT *\r\n" + 
					"    FROM (\r\n" + 
					"            SELECT numero\r\n" + 
					"            FROM HABITACION\r\n" + 
					"            WHERE numero not in\r\n" + 
					"            (\r\n" + 
					"                SELECT numeroHabitacion\r\n" + 
					"                FROM USUARIO\r\n" + 
					"				 WHERE numeroHabitacion is not null" +
					"\r\n" + 
					"            ) \r\n" + 
					"\r\n" + 
					"         )\r\n" + 
					"     WHERE rownum = 1  \r\n" + 
					"    )\r\n" + 
					"WHERE numerodocumento = ?" ;
					
			Query q = pm.newQuery(SQL, sql );
			q.setParameters(idUsuario);
			q.executeUnique();

		}

		public String getNumeroHabitacion(PersistenceManager pm, String idUsuario) {
			Query q = pm.newQuery(SQL,"SELECT numeroHabitacion FROM USUARIO WHERE numeroDocumento = ?" );
			q.setResultClass(String.class);
			q.setParameters(idUsuario);
			return (String) q.executeUnique();
		}

		public String getPagosUsuarios(PersistenceManager pm, String idUsuario) {
			
			Query q = pm.newQuery(SQL,"SELECT pagos FROM USUARIO WHERE numeroDocumento = ?" );
			q.setResultClass(String.class);
			q.setParameters(idUsuario);
			return (String) q.executeUnique();
		}

		public void updatePagos(PersistenceManager pm, String pago, String idUsuario) {
			
			Query q = pm.newQuery(SQL, "UPDATE " + persistencia.darTablaUsuario () + " SET pagos = ? WHERE numeroDocumento = ?");
		    q.setParameters(pago, idUsuario );
		     
		    q.executeUnique();
			
		}

		public List<Usuario> getBuenosCLientes(PersistenceManager pm) {
	
	return null;
//			Query q = pm.newQuery(SQL,"SELECT pagos FROM USUARIO WHERE numeroDocumento = ?" );
//			q.setResultClass(String.class);
//			q.setParameters(idUsuario);
//			return (String) q.executeUnique();
		}
		
		public List<Object[]> getClientsByServiceConsumption(PersistenceManager pm , String serviceName, String inicialDate, String endDate, String groupByColumn, String orderByColumn){
			
			String select = "*";//select all por defecto
			if(groupByColumn.equals("N/A"))
				groupByColumn = "";
			else{
				select =  groupByColumn + " , COUNT(*) ";
				groupByColumn = " GROUP BY " + groupByColumn;
			}
			if(orderByColumn.equals("N/A"))
				orderByColumn = "";
			else{
				orderByColumn = " ORDER BY " + orderByColumn;
			}
			
			Query<Object[]> q = pm.newQuery(SQL,  "SELECT " + select + "FROM usuario u ,consumo c,servicio s \r\n" + 
					"    where  s.id = c.idserviciocargado AND s.nombre = ? AND c.idusuario = u.numerodocumento \r\n" + 
					"    AND c.fecharegistro BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') AND TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') " + groupByColumn + " " + orderByColumn);
			
			q.setParameters(serviceName , inicialDate, endDate);
//			q.setResultClass(Usuario.class);
			return q.executeList();
		}
		
		public List<Object[]> getClientsNotInServiceConsumptio(PersistenceManager pm, String serviceName, String inicialDate, String endDate, 
				 String groupByColumn, String orderByColumn){
			
			String select = "*";//select all por defecto
			if(groupByColumn.equals("N/A"))
				groupByColumn = " ";
			else{
				select =  groupByColumn + " , COUNT(*) ";
				groupByColumn = " GROUP BY " + groupByColumn;
			}
			if(orderByColumn.equals("N/A"))
				orderByColumn = " ";
			else{
				orderByColumn = " ORDER BY " + orderByColumn;
			}
			
			Query<Object[]> q = pm.newQuery(SQL, " SELECT " + select + " FROM Usuario where numerodocumento not in (\r\n" + 
					"    SELECT u.numerodocumento FROM usuario u ,consumo c,servicio s\r\n" + 
					"    where  s.id = c.idserviciocargado AND s.nombre = ? AND c.idusuario = u.numerodocumento AND c.fecharegistro BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') AND TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')       \r\n" + 
					"    ) "  );
			q.setParameters(serviceName , inicialDate, endDate);
//			q.setResultClass(Usuario.class);
			return q.executeList();
		}
		
}

