/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;


import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.PlanConsumo;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.RolDeUsuario;
import uniandes.isis2304.parranderos.negocio.Servicio;
import uniandes.isis2304.parranderos.negocio.TipoHabitacion;
import uniandes.isis2304.parranderos.negocio.Usuario;


/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class Persistencia 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Persistencia.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";
	

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static Persistencia instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	private SQLRolDeUsuario sqlRolDeUsuario;
	
	private SQLUsuario sqlUsuario;
	
	private SQLTipoHabitacion sqlTipoHabitacion;
	
	private SQLHabitacion sqlHabitacion;
	
	private SQLReserva sqlReserva;
	
	private SQLPlanConsumo sqlPlanConsumo;
	
	private SQLConsumo sqlConsumo;
	
	private SQLServicio sqlServicio;
	
	private SQLProducto sqlProducto;
	
	
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private Persistencia ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("HotelAndes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("HotelAndes_sequence");
		tablas.add("USUARIO");
		tablas.add ("ROLDEUSUARIO");
		tablas.add ("TIPOHABITACION");
		tablas.add("HABITACION");
		tablas.add ("RESERVA");
		tablas.add("PLANCONSUMO");
		tablas.add("CONSUMO");
		tablas.add("SERVICIO");
		tablas.add("PRODUCTO");

	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private Persistencia (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static Persistencia getInstance ()
	{
		if (instance == null)
		{
			instance = new Persistencia ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static Persistencia getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new Persistencia (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{

		sqlUtil = new SQLUtil(this);
		sqlRolDeUsuario = new SQLRolDeUsuario(this);
		sqlUsuario = new SQLUsuario(this);
		sqlTipoHabitacion = new SQLTipoHabitacion(this);
		sqlHabitacion = new SQLHabitacion(this);
		sqlReserva = new SQLReserva(this);
		sqlPlanConsumo = new SQLPlanConsumo(this);
		sqlConsumo = new SQLConsumo(this);
		sqlServicio = new SQLServicio(this);
		sqlHabitacion = new SQLHabitacion(this);
		sqlProducto = new SQLProducto(this);
		
		
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de hotelandes
	 */
	public String darSeqHotelAndes ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoBebida de parranderos
	 */
	public String darTablaUsuario ()
	{
		return tablas.get (1);
	}
	
	public String darTablaRolesDeUsuario()
	{		
		return tablas.get(2);
	}
	public String darTablaTipoHabitacion(){
		
		return tablas.get(3);
	}
	public String darTablaHabitacion(){
		return tablas.get(4);
	}
	public String darTablaReserva(){
		return tablas.get(5);
	}
	public String darTablaPlanConsumo(){
		return tablas.get(6);
	}
	public String darTablaConsumo(){
		return tablas.get(7);
	}
	public String darTablaServicio(){
		return tablas.get(8);
	}
	public String darTablaProducto(){
		return tablas.get(9);
	}
	
	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	/* ****************************************************************
	 * 			CRUDs requeridos
	 *****************************************************************/
	
	
	public RolDeUsuario getRolDeUsuario(String cargo){
		
		 
		return sqlRolDeUsuario.getRolDeUsuario(pmf.getPersistenceManager(),cargo);
	}
	public TipoHabitacion getTipoHabitacion(String nombre)
	{
		TipoHabitacion s = sqlTipoHabitacion.getTipoHabitacion(pmf.getPersistenceManager(),nombre);
		return s;
		
	}
	public String cambiarCantidadDisponibleTipoHabitacion(String nombre, Integer nuevaCapacidad){
		return sqlTipoHabitacion.cambiarCantidadDisponible(pmf.getPersistenceManager(), nombre, nuevaCapacidad);
	}
	
	public PlanConsumo getPlanConsumo(String nombre){
		return sqlPlanConsumo.getPlanConsumo(pmf.getPersistenceManager(),nombre);
	}
	
	public Usuario getUsuario(String id){
		return sqlUsuario.getUsuario(pmf.getPersistenceManager(),id);
	}
	public long darTotalHabitaciones(String tipoHabitacion) 
	{
		
		return sqlHabitacion.darTotalHabitaciones( pmf.getPersistenceManager(), tipoHabitacion );
	}
	public Habitacion getHabitacion(String numeroHab)
	{
		return sqlHabitacion.getHabitacion(pmf.getPersistenceManager(), numeroHab);
	}
	public long getReservasHabitacionEn(String tipoHabitacion, Timestamp fechaCo, Timestamp fechaFi) {
		// TODO Auto-generated method stub
		
		return sqlReserva.darReservasHabitacion(pmf.getPersistenceManager(), tipoHabitacion,fechaCo, fechaFi);
	}
	public Producto getProducto(String id){
		return sqlProducto.getProducto(pmf.getPersistenceManager(), id);
	}
	public Servicio getServicio(String id){
		return sqlServicio.getServicio(pmf.getPersistenceManager(), id);
		
	}
	public Reserva getReserva(String idUsuario, Timestamp llegada){
		return sqlReserva.getReservado(pmf.getPersistenceManager(),idUsuario, llegada);
	}
	public Reserva getReservaBySalida(String idUsuario, Timestamp fechaSalida) {
		return sqlReserva.getReservadoBySalida(pmf.getPersistenceManager(),idUsuario, fechaSalida);
	}
	//desde aquí
	public int getCantidadDeHabitacionPorTipo(String tipo){
		return sqlHabitacion.getCantidadDeHabitacionPorTipo(pmf.getPersistenceManager(),tipo);
	}
	public int getReservasTipoHabitacionXFecha(Timestamp fechaInic , Timestamp fechaFin, String tipoHabitacion){
		return sqlReserva.getReservasTipoHabitacionXFecha(pmf.getPersistenceManager(),fechaInic, fechaFin, tipoHabitacion);
	}
//	public String getDescripcionServicio(String nombre){
//		
//	}
//	public int getCantidadReservada()

	/* ****************************************************************
	 * 			REQUERIMIENTOS FUNCIONALES
	 *****************************************************************/

	//registrarRolDeUsuario RF1
	public RolDeUsuario adicionarRolesDeUsuario(String cargo, String desc) {
		PersistenceManager pm = pmf.getPersistenceManager();
      Transaction tx=pm.currentTransaction();
      try
      {
          tx.begin();
          String tuplasInsertadas = sqlRolDeUsuario.adicionarRolDeUsuario(pm, cargo, desc);
          tx.commit();
          
          log.trace ("Inserción de rol de usuario: " + cargo + ": " + tuplasInsertadas + " tuplas insertadas");
          
          return new RolDeUsuario( cargo, desc );
      }
      catch (Exception e)
      {
//      	e.printStackTrace();
      	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
      	return null;
      }
      finally
      {
          if (tx.isActive())
          {
              tx.rollback();
          }
          pm.close();
      }
	}
	
	//registrarUsuario RF2
	public Usuario adicionarUsuario(String nombre,  String edad,  String tel,  String tipoDoc, String numeroDoc,
			 String correo,  String cargo) 
	{
	  PersistenceManager pm = pmf.getPersistenceManager();
      Transaction tx=pm.currentTransaction();
      try
      {
          tx.begin();
          String tuplasInsertadas = sqlUsuario.adicionarUsuario(pm,nombre,  edad,  tel,  tipoDoc,  numeroDoc,
					 correo,  null,  null,  cargo,  null);
          tx.commit();
          
          log.trace ("Inserción de usuario: " + numeroDoc + ": " + tuplasInsertadas + " tuplas insertadas");
          
          return new Usuario(  nombre,  edad,  tel,  tipoDoc,  numeroDoc,
      			 correo,  null,  null,  cargo,  null);
      }
      catch (Exception e)
      {
//      	e.printStackTrace();
      	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
      	return null;
      }
      finally
      {
          if (tx.isActive())
          {
              tx.rollback();
          }
          pm.close();
      }
	}
	
	//registrar tipo de habitación RF3
	public TipoHabitacion adicionarTipoDeHabitacion(String nombre,  Integer capacidad,  Integer costoPorNoche, Integer cantidadDisponible ) 
	{
	  PersistenceManager pm = pmf.getPersistenceManager();
     Transaction tx=pm.currentTransaction();
     try
     {
         tx.begin();
         String tuplasInsertadas = sqlTipoHabitacion.adicionarTipoHabitacion(pm,nombre,  capacidad,  costoPorNoche,  cantidadDisponible);
         tx.commit();
         
         log.trace ("Inserción de tipo de habitación: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
         
         return new TipoHabitacion(  nombre, capacidad, costoPorNoche, cantidadDisponible);
     }
     catch (Exception e)
     {
//     	e.printStackTrace();
     	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
     	return null;
     }
     finally
     {
         if (tx.isActive())
         {
             tx.rollback();
         }
         pm.close();
     }
	}
	//registrar habitación RF4
	public Habitacion adicionarHabitacion(String numero, String tipoHabitacion){
	  PersistenceManager pm = pmf.getPersistenceManager();
     Transaction tx=pm.currentTransaction();
     try
     {
         tx.begin();
         String tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm,numero,  tipoHabitacion);
         tx.commit();
         
         log.trace ("Inserción de tipo de habitación: " + numero + ": " + tuplasInsertadas + " tuplas insertadas");
         
         return new Habitacion(  numero, getTipoHabitacion(tipoHabitacion));
     }
     catch (Exception e)
     {
//     	e.printStackTrace();
     	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
     	return null;
     }
     finally
     {
         if (tx.isActive())
         {
             tx.rollback();
         }
         pm.close();
     }
	}
	//registrar servicio RF5
	public Servicio adicionarServicio(String nombre, String descripcion, int costo){
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idServicio = nextval ();
            long tuplasInsertadas = sqlServicio.adicionarServicio(pmf.getPersistenceManager(), idServicio, nombre, descripcion, costo);
            tx.commit();

            log.trace ("Inserción de servicio: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Servicio (idServicio, nombre, descripcion, costo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	//registrar plan consumo RF6
	public PlanConsumo adicionarPlanConsumo(String nombre, int porcentajeDescuento, String des){
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            String tuplasInsertadas = sqlPlanConsumo.adicionarPlanConsumo(pmf.getPersistenceManager(), nombre, porcentajeDescuento, des);
            tx.commit();

            log.trace ("Inserción de plan de consumo: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new PlanConsumo (nombre, porcentajeDescuento, des);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
		
	}
	//RF8
	public Reserva adicionarReserva(String metodoDePago, int numeroPersonas, Timestamp fechaComienzo, Timestamp fechaFin, String tipoHabitacion, String planConsumo, String idUsuario, String idProducto){
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlReserva.adicionarReserva(pmf.getPersistenceManager(), id, metodoDePago, numeroPersonas, fechaComienzo, fechaFin, tipoHabitacion, planConsumo, idUsuario, "-1");
            tx.commit();

            log.trace ("Inserción de Reserva: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Reserva (id+"", metodoDePago, numeroPersonas, fechaComienzo, fechaFin, tipoHabitacion, planConsumo, idUsuario, idProducto);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	//RF8
	public Producto adicionarProducto(String nombre, String duracion, String idServicio){
		
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlProducto.adicionarProducto(pmf.getPersistenceManager(), id, nombre, duracion, idServicio);
            tx.commit();

            log.trace ("Inserción de producto: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Producto (id+"", nombre, Integer.parseInt(duracion), Long.parseLong(idServicio));
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	public Consumo adicionarConsumo(String valor, Timestamp fechaRegistro, String numeroHabitacionACargar, String idServicioACargar){
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlConsumo.adicionarConsumo(pmf.getPersistenceManager(), id, valor, fechaRegistro, numeroHabitacionACargar, idServicioACargar);
            tx.commit();

            log.trace ("Inserción de consumo: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Consumo (id, Integer.parseInt(valor), fechaRegistro, numeroHabitacionACargar, Long.parseLong(idServicioACargar));
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	//RF9
	public String registrarLlegadaUsuario(String idUsuario, Timestamp llegada){
		
		Reserva reserva = getReserva(idUsuario, llegada);
		if( reserva== null) return "";
		
		System.out.println("existe reserva con el usuario, id: ");
		System.out.println(reserva.getId());
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            String id = sqlUsuario.updateReserva(pm,idUsuario, llegada);
            System.out.println("sale de updateReserva");
            tx.commit();
            return id;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return "";
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}

	public String registrarSalidaUsuario(String idUsuario, Timestamp fechaSalida) {
		Reserva reserva = getReservaBySalida(idUsuario, fechaSalida);
		if( reserva== null) return "";
		
		System.out.println("existe reserva con el usuario, id: ");
		System.out.println(reserva.getId());
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            String id = sqlUsuario.updateReservaBySalida(pm,idUsuario, fechaSalida);
            System.out.println("sale de updateReserva");
            tx.commit();
            return id;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return "";
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	
	
//	public String eliminarRolDeUsuario( String cargo){
//		
//		PersistenceManager pm = pmf.getPersistenceManager();
//	      Transaction tx=pm.currentTransaction();
//	      try
//	      {
//	          tx.begin();
//	          String tuplasInsertadas = sqlRolDeUsuario.adicionarRolDeUsuario(pm, cargo, desc);
//	          tx.commit();
//	          
//	          log.trace ("Inserción de rol de usuario: " + cargo + ": " + tuplasInsertadas + " tuplas insertadas");
//	          
//	          return new RolDeUsuario( cargo, desc );
//	      }
//	      catch (Exception e)
//	      {
////	      	e.printStackTrace();
//	      	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//	      	return null;
//	      }
//	      finally
//	      {
//	          if (tx.isActive())
//	          {
//	              tx.rollback();
//	          }
//	          pm.close();
//	      }
//		}
//		
//	}

	

	


}