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

package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.HotelAndes;
import uniandes.isis2304.parranderos.negocio.PlanConsumo;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.RolDeUsuario;
import uniandes.isis2304.parranderos.negocio.Servicio;
import uniandes.isis2304.parranderos.negocio.TipoHabitacion;
import uniandes.isis2304.parranderos.negocio.Usuario;


/**
 * Clase principal de la interfaz
 * @author Wan y Sai
 */
@SuppressWarnings("serial")

public class InterfazParranderosApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazParranderosApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private HotelAndes hotelAndes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazParranderosApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        hotelAndes = new HotelAndes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    public void adicionarRolDeUsuario(){
    	try 
    	{
    		String cargo = JOptionPane.showInputDialog (this, "Cual rol de usuario?", "Adicionar Rol de Usuario", JOptionPane.QUESTION_MESSAGE);
    		String desc = JOptionPane.showInputDialog (this, "Descripcion", "Adicionar Rol de Usuario", JOptionPane.QUESTION_MESSAGE);
    		if (cargo != null && desc != null)
    		{
        		RolDeUsuario ru = hotelAndes.adicionarRolDeUsuario (cargo, desc);
        		if (ru == null)
        		{
        			throw new Exception ("No se pudo crear un rol de usuario con nombre: " + cargo);
        		}
        		String resultado = "En adicionarRolDeUsuario\n\n";
        		resultado += "Rol de usuario adicionado exitosamente: " + ru;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void registrarUsuario(){
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre y Apellido", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE);
    		String rol = JOptionPane.showInputDialog (this, "Rol", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE);
    		String edad = JOptionPane.showInputDialog (this, "Edad", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE);
    		String tel = JOptionPane.showInputDialog (this, "Telefono", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE);
    		String tipoDoc = JOptionPane.showInputDialog (this, "Tipo de Documento", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE);
    		String numeroDoc = JOptionPane.showInputDialog (this, "Numero documento", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE);
    		String correo = JOptionPane.showInputDialog (this, "Correo", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE);
    		if (nombre != null && rol != null && edad != null && tel != null && tipoDoc != null && numeroDoc != null && correo != null )
    		{
    			
        		Usuario u = hotelAndes.registrarUsuario ( nombre,  edad,  tel,  tipoDoc, numeroDoc,  correo, rol );
        		if (u == null)
        		{
        			throw new Exception ("No se pudo crear un rol de usuario con nombre: " + numeroDoc);
        		}
        		String resultado = "En registrar Usuario\n\n";
        		resultado += "usuario registrado exitosamente: " + u;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarTipoDeHabitacion()
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre", "Registrar tipo de habitacion", JOptionPane.QUESTION_MESSAGE);
    		String capacidad = JOptionPane.showInputDialog (this, "Capacidad", "Registrar tipo de habitacion", JOptionPane.QUESTION_MESSAGE);
    		String costoXNoche = JOptionPane.showInputDialog (this, "Costo por noche", "Registrar tipo de habitacion", JOptionPane.QUESTION_MESSAGE);
    		String cantidadDisponible = JOptionPane.showInputDialog (this, "Cantidad Disponible", "Registrar tipo de habitacion", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		if (nombre != null && capacidad != null && costoXNoche != null && cantidadDisponible != null)
    		{
    			
        		TipoHabitacion th = hotelAndes.adicionarTipoDeHabitacion ( nombre, capacidad, costoXNoche, cantidadDisponible );
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear un tipo de habitacion con nombre: " + nombre);
        		}
        		String resultado = "En registrar tipo de habitacion\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarHabitacion()
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Número", "Registrar habitacion", JOptionPane.QUESTION_MESSAGE);
    		String tipoHabitacion = JOptionPane.showInputDialog (this, "Tipo de Habitacion", "Registrar habitacion", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		
    		if (nombre != null && tipoHabitacion != null )
    		{
    			
        		Habitacion th = hotelAndes.adicionarHabitacion ( nombre, tipoHabitacion);
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear un tipo de habitacion con nombre: " + nombre);
        		}
        		String resultado = "En registrar tipo de habitacion\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarServicio()
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre", "Registrar servicio", JOptionPane.QUESTION_MESSAGE);
    		String descripcion = JOptionPane.showInputDialog (this, "Descripcion", "Registrar servicio", JOptionPane.QUESTION_MESSAGE);
    		String costo = JOptionPane.showInputDialog (this, "Costo", "Registrar servicio", JOptionPane.QUESTION_MESSAGE);

    		
    		
    		if (nombre != null && descripcion != null && costo != null )
    		{
    			
        		Servicio th = hotelAndes.adicionarServicio ( nombre, descripcion, costo);
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear un servicio con nombre: " + nombre);
        		}
        		String resultado = "En registrar servicio\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarPlanConsumo()
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre", "Registrar Plan Consumo", JOptionPane.QUESTION_MESSAGE);
    		String des = JOptionPane.showInputDialog (this, "Descripcion", "Registrar Plan Consumo", JOptionPane.QUESTION_MESSAGE);
    		String por = JOptionPane.showInputDialog (this, "Porcentaje de Descuento", "Registrar Plan Consumo", JOptionPane.QUESTION_MESSAGE);

    		
    		
    		if (nombre != null && por != null && des != null  )
    		{
    			
        		PlanConsumo th = hotelAndes.adicionarPlanConsumo( nombre, por, des);
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear un servicio con nombre: " + nombre);
        		}
        		String resultado = "En registrar plan consumo\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarReservaAlojamiento()
    {
    	try 
    	{
    		String idUsuario = JOptionPane.showInputDialog (this, "numero de identificacion del usuario", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String metodoDePago = JOptionPane.showInputDialog (this, "Metodo de pago", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String numeroPersonas = JOptionPane.showInputDialog (this, "Numero de Personas", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaComienzo = JOptionPane.showInputDialog (this, "Fecha comienzo\nFormato: yyyy-mm-dd", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaFin = JOptionPane.showInputDialog (this, "Fecha fin\nFormato: yyyy-mm-dd", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String tipoHabitacion = JOptionPane.showInputDialog (this, "Tipo de Habitacion", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String planConsumo = JOptionPane.showInputDialog (this, "Plan Consumo", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);

    		
    		
    		if (idUsuario != null && metodoDePago != null && numeroPersonas != null && fechaComienzo != null && fechaFin != null && tipoHabitacion != null && planConsumo != null )
    		{
    			
        		Reserva th = hotelAndes.adicionarReserva(  metodoDePago,  numeroPersonas,  fechaComienzo+" 00:00:00",  fechaFin+" 00:00:00",
        				 tipoHabitacion,  planConsumo,  idUsuario, "-1");
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear una reserva para el usuario con id "+ idUsuario);
        		}
        		String resultado = "En registrar plan consumo\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarReservaServicio()
    {
    	try 
    	{
    		String idUsuario = JOptionPane.showInputDialog (this, "Numero de identificacion del usuario", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String metodoDePago = JOptionPane.showInputDialog (this, "Metodo de pago", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String cantidadPersonas = JOptionPane.showInputDialog (this, "Cantidad de Personas", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaComienzo = JOptionPane.showInputDialog (this, "Fecha comienzo\nFormato: yyyy-mm-dd hh:mm", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaFin = JOptionPane.showInputDialog (this, "Fecha fin\nFormato: yyyy-mm-dd hh:mm", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String idProducto = JOptionPane.showInputDialog (this, "id del Producto", "Registrar Reserva", JOptionPane.QUESTION_MESSAGE);

    		
    		
    		if (idUsuario != null && metodoDePago != null && fechaComienzo != null && cantidadPersonas != null && fechaFin != null && idProducto != null )
    		{
    			
        		Reserva th = hotelAndes.adicionarReserva(  metodoDePago,  cantidadPersonas,  fechaComienzo+":00",  fechaFin+":00",
        				 "",  "",  idUsuario, idProducto);
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear una reserva para el usuario con id "+ idUsuario);
        		}
        		String resultado = "En registrar plan consumo\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarProducto()
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre Producto", "Registrar Producto", JOptionPane.QUESTION_MESSAGE);
    		String duracion = JOptionPane.showInputDialog (this, "Duracion", "Registrar Producto", JOptionPane.QUESTION_MESSAGE);
    		String idServicio = JOptionPane.showInputDialog (this, "id Del Servicio", "Registrar Producto", JOptionPane.QUESTION_MESSAGE);

    		
    		
    		if (nombre != null && duracion != null && idServicio != null  )
    		{
    			
        		Producto th = hotelAndes.adicionarProducto(  nombre, duracion, idServicio);
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear una producto para el usuario con id "+ nombre);
        		}
        		String resultado = "En registrar plan consumo\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarConsumo()
    {
    	try 
    	{
    		String valor = JOptionPane.showInputDialog (this, "Valor de consumo", "Registrar Consumo", JOptionPane.QUESTION_MESSAGE);
    		String numeroHabitacionACargar = JOptionPane.showInputDialog (this, "Numero Habitacion", "Registrar Consumo", JOptionPane.QUESTION_MESSAGE);
    		String idServicio = JOptionPane.showInputDialog (this, "id del Servicio", "Registrar Consumo", JOptionPane.QUESTION_MESSAGE);

    		
    		
    		if (valor != null && numeroHabitacionACargar != null && idServicio != null  )
    		{
    			
        		Consumo th = hotelAndes.adicionarConsumo(  valor, numeroHabitacionACargar, idServicio);
        		if (th == null)
        		{
        			throw new Exception ("No se pudo crear el consumo con valor "+ valor+ " para el servicio "+idServicio);
        		}
        		String resultado = "En registrar plan consumo\n\n";
        		resultado += "tipo de habitacion exitosamente: " + th;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void registrarLlegadaCliente()
    {
    	try 
    	{
    		String idUsuario = JOptionPane.showInputDialog (this, "Id Usuario", "Registrar Llegada Cliente", JOptionPane.QUESTION_MESSAGE);
    		String fechaLlegada = JOptionPane.showInputDialog (this, "Fecha comienzo\nFormato: yyyy-mm-dd", "Registrar Llegada Cliente", JOptionPane.QUESTION_MESSAGE);
    		if (idUsuario != null && fechaLlegada != null   )
    		{
    			
        		if (!hotelAndes.registrarLlegadaCliente(  idUsuario, fechaLlegada+" 00:00:00" ))
        		{
        			throw new Exception ("No se pudo registrar la llegada del cliente con id" + idUsuario);
        		}
        		String resultado = "En registrar llegada cliente\n\n";
        		resultado += "tipo de habitacion exitosamente: " + idUsuario;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void registrarSalidaCliente()
    
    {
    	try 
    	{
    		String idUsuario = JOptionPane.showInputDialog (this, "Id Usuario", "Registrar Llegada Cliente", JOptionPane.QUESTION_MESSAGE);
    		String fechaSalida = JOptionPane.showInputDialog (this, "Fecha comienzo\nFormato: yyyy-mm-dd", "Registrar Llegada Cliente", JOptionPane.QUESTION_MESSAGE);
    		if (idUsuario != null && fechaSalida != null   )
    		{
    			
        		if (!hotelAndes.registrarSalidaCliente(  idUsuario, fechaSalida+" 00:00:00" ))
        		{
        			throw new Exception ("No se pudo registrar la llegada del cliente con id" + idUsuario);
        		}
        		String resultado = "En registrar llegada cliente\n\n";
        		resultado += "tipo de habitacion exitosamente: " + idUsuario;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
			
		}
    	
    	
    }
    public void reservarAlojamientoYServicios()
	{
    	try 
    	{
    		String idUsuario = JOptionPane.showInputDialog (this, "Digite por favor el documento de identidad", "Reservar alojamiento y servicios", JOptionPane.QUESTION_MESSAGE);
    		if( hotelAndes.getUsuario(idUsuario) == null) throw new Exception ("No existe el usuario");
    		String alojamientos = JOptionPane.showInputDialog (this, "Alojamiento(s)\nFormato:<Tipo Habitacion 1>:<Cantidad>; ...", "Reservar alojamiento y servicios", JOptionPane.QUESTION_MESSAGE);
    		String fechaComienzoAlojamiento = JOptionPane.showInputDialog (this, "Fecha inicio del alojamiento\nFormato:yyyy-mm-dd", "Reservar alojamiento y servicios", JOptionPane.QUESTION_MESSAGE);
    		String fechaFinAlojamiento = JOptionPane.showInputDialog (this, "Fecha fin del alojamiento\nFormato:yyyy-mm-dd", "Reservar alojamiento y servicios", JOptionPane.QUESTION_MESSAGE);
    		String servicios = JOptionPane.showInputDialog (this, "Ingrese nombre de servicios, cantidad personas, fecha comienzo, fecha fin\n"
    				+ "<Nombre>#<Cantidad>#yyyy-mm-dd hh:mm#yyyy-mm-dd hh:mm", "Reservar alojamiento y servicios", JOptionPane.QUESTION_MESSAGE);

    		//String servicio = JOptionPane.showInputDialog (this, "Servicios\nFormato:<Nombre servicio 1>;<Nombre servicio 2>; ...", "Reservar alojamiento y servicios", JOptionPane.QUESTION_MESSAGE);
    		if(alojamientos != null && fechaComienzoAlojamiento != null && fechaFinAlojamiento != null && servicios != null  )
    		{
    			hotelAndes.reservarAlojamientoYServicio(alojamientos, fechaComienzoAlojamiento+" 00:00:00", fechaFinAlojamiento+" 00:00:00",servicios,idUsuario );
    			panelDatos.actualizarInterfaz("Fin del requerimiento");
    				
    		}
    		else 
			{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
    		
    		

    			
    		
		} 
    	catch (Exception e) 
    	{
//			exception.printStackTrace()
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
    public void cancelarReservasConvencion()
    {
    	try 
    	{
    		String idUsuario = JOptionPane.showInputDialog (this, "Digite por favor el documento de identidad", "Reservar alojamiento y servicios", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		if(idUsuario != null )
    		{
    			String message = "Se borraron "+hotelAndes.cancelarReservaConvencion( idUsuario )+" reservas de la base de datos.";
    			panelDatos.actualizarInterfaz(message+"\nSuccess");
    		}
    		else 
			{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
    	}
    	catch( Exception e)
    	{
    		String message = "\nError. Vuelva a intentar";
			panelDatos.actualizarInterfaz(generarMensajeError(e)+message);
    	}
    }
    public void registrarFinConvencion()
    {
    	
    }
    public void registrarEntradaMantenimientoAlojamientosYServicios()
    {
    	
    }
    public void registrarFinMantenimientoAlojamientosYServicios()
    {
    	
    }
    
    
	/* ****************************************************************
	 * 			CRUD de TipoBebida
	 *****************************************************************/

    

    /**
     * Consulta en la base de datos los tipos de bebida existentes y los muestra en el panel de datos de la aplicación
     */
//    public void listarTipoBebida( )
//    {
//    	try 
//    	{
//			List <VOTipoBebida> lista = parranderos.darVOTiposBebida();
//
//			String resultado = "En listarTipoBebida";
//			resultado +=  "\n" + listarTiposBebida (lista);
//			panelDatos.actualizarInterfaz(resultado);
//			resultado += "\n Operación terminada";
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//
//    /**
//     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
//     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
//     */
//    public void eliminarTipoBebidaPorId( )
//    {
//    	try 
//    	{
//    		String idTipoStr = JOptionPane.showInputDialog (this, "Id del tipo de bedida?", "Borrar tipo de bebida por Id", JOptionPane.QUESTION_MESSAGE);
//    		if (idTipoStr != null)
//    		{
//    			long idTipo = Long.valueOf (idTipoStr);
//    			long tbEliminados = parranderos.eliminarTipoBebidaPorId (idTipo);
//
//    			String resultado = "En eliminar TipoBebida\n\n";
//    			resultado += tbEliminados + " Tipos de bebida eliminados\n";
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//
//    /**
//     * Busca el tipo de bebida con el nombre indicado por el usuario y lo muestra en el panel de datos
//     */
//    public void buscarTipoBebidaPorNombre( )
//    {
//    	try 
//    	{
//    		String nombreTb = JOptionPane.showInputDialog (this, "Nombre del tipo de bedida?", "Buscar tipo de bebida por nombre", JOptionPane.QUESTION_MESSAGE);
//    		if (nombreTb != null)
//    		{
//    			VOTipoBebida tipoBebida = parranderos.darTipoBebidaPorNombre (nombreTb);
//    			String resultado = "En buscar Tipo Bebida por nombre\n\n";
//    			if (tipoBebida != null)
//    			{
//        			resultado += "El tipo de bebida es: " + tipoBebida;
//    			}
//    			else
//    			{
//        			resultado += "Un tipo de bebida con nombre: " + nombreTb + " NO EXISTE\n";    				
//    			}
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }


	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
//	public void limpiarBD ()
//	{
//		try 
//		{
//    		// Ejecución de la demo y recolección de los resultados
//			long eliminados [] = parranderos.limpiarParranderos();
//			
//			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
//			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
//			resultado += eliminados [0] + " Gustan eliminados\n";
//			resultado += eliminados [1] + " Sirven eliminados\n";
//			resultado += eliminados [2] + " Visitan eliminados\n";
//			resultado += eliminados [3] + " Bebidas eliminadas\n";
//			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
//			resultado += eliminados [5] + " Bebedores eliminados\n";
//			resultado += eliminados [6] + " Bares eliminados\n";
//			resultado += "\nLimpieza terminada";
//   
//			panelDatos.actualizarInterfaz(resultado);
//		} 
//		catch (Exception e) 
//		{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germán Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
//    private String listarTiposBebida(List<VOTipoBebida> lista) 
//    {
//    	String resp = "Los tipos de bebida existentes son:\n";
//    	int i = 1;
//        for (VOTipoBebida tb : lista)
//        {
//        	resp += i++ + ". " + tb.toString() + "\n";
//        }
//        return resp;
//	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazParranderosApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazParranderosApp interfaz = new InterfazParranderosApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
