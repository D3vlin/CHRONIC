package co.edu.iumafis.chronic.model.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * This class handles the connection to the database.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-17
 */
public class ResourceManager {
    
    private static final String JDBC_DRIVER   = "com.mysql.cj.jdbc.Driver";
    private static String JDBC_URL;
    private static String JDBC_USER;
    private static String JDBC_PASSWORD;
    private static Driver driver = null;
    
    /**
     * Get the connection data to the database.
     * 
     * @return String[]
     */
    public static String[] getDataConnection() {
        String[] data = null;
                           
        try {
            data = new String[3];
            FileReader fReader = new FileReader("connection.dat");
            BufferedReader bReader = new BufferedReader(fReader);
            String line;
            int number = 0;

            while((line = bReader.readLine()) != null) {
                data[number] = line.substring(line.indexOf("=") + 1, line.length());
                number++;
                if(number > 2) { break; }
            }
         
        } catch (IOException exception) {            
            JOptionPane.showMessageDialog(null, "Error al acceder al archivo connection.dat", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        return data;
    }
    
    /**
     * Establishes the connection to the database.
     * 
     * @return boolean
     * @throws SQLException 
     */
    public static boolean setConnection() throws SQLException {        
        boolean ok = false;
        
        String[] dataConection = getDataConnection();

        if (dataConection[0] != null) {
            
            JDBC_URL = dataConection[0];
            JDBC_USER = dataConection[1];
            JDBC_PASSWORD = dataConection[2];

            try {                
                close(getConnection());            
                ok = true;
                
            } catch (SQLException exception) {                
                JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);                
            }
        }
         
        return ok;  
    }
    
    /**
     * Gets the connection to the database.
     * 
     * @return Connection
     * @throws SQLException 
     */
    public static synchronized Connection getConnection() throws SQLException {        
        if (driver == null) {    
            try {                
                Class jdbcDriverClass = Class.forName( JDBC_DRIVER );
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver( driver );
            
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException exception) {                
                //
            }
        }

        return DriverManager.getConnection (JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
    
    /**
     * Close the connection to the database.
     * 
     * @param conn 
     */
    public static void close(Connection conn) {  
        try { 
            if (conn != null) conn.close();
        
        } catch (SQLException exception) {        
            //
        }
    }
    
    /**
     * Close the statement.
     * 
     * @param stmt 
     */
    public static void close(PreparedStatement stmt) {   
        try {
            if (stmt != null) stmt.close(); 
        
        } catch (SQLException exception) {
            //
        }
    }

    /**
     * Close the resultSet.
     * 
     * @param rs 
     */
    public static void close(ResultSet rs) {  
        try {
            if (rs != null) rs.close(); 
        
        } catch (SQLException exception) {
            //
        }
    }
}
