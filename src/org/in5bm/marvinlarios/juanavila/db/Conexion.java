package org.in5bm.marvinlarios.juanavila.db;

/**
 *
 * @Author     : Marvin Larios Cutzal
    Carne 2021097
    @Autor      : Juan Avila Flores
    Carne 2021047
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class Conexion {
    
     private final String URL;
    private final String IP_SERVER = "localhost";
    private final String PORT = "3306";
    private final String DB = "db_control_academico_in5bm";
    private final String USER = "root";
    private final String PASSWORD = "admin";
    private Connection conexion;
    
    private static Conexion instancia;

    public static Conexion getInstance() {

        if (instancia == null) {
            instancia = new Conexion();
        }
        
        return instancia;
    }

    private Conexion() {

        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión Exitosa");
            
            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion() + "\n");

        } catch (ClassNotFoundException e) {
            System.err.println("No se encuentra ninguna definición para la clase");
            e.printStackTrace();
            
            
        } catch (CommunicationsException e) {
            System.err.println("No está levantado el servicio de MySQL o el HOST es incorrecto");
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("Se produjo un error de tipo SQLException");
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Se produjo un error al intentar establecer una conexión con la base de datos");
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}
