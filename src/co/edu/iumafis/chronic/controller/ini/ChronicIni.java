package co.edu.iumafis.chronic.controller.ini;

import co.edu.iumafis.chronic.controller.login.CLogin;
import static co.edu.iumafis.chronic.model.dao.ResourceManager.setConnection;
import java.sql.SQLException;

/**
 * Main Class
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-17
 */
public final class ChronicIni {

    /**
     * Start the application.
     * 
     * @param args
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException { ChronicIni chronicIni = new ChronicIni(); }
    
    /**
     * Main constructor, initializes the plaf JTattoo library.
     * @throws java.sql.SQLException
     */
    public ChronicIni() throws SQLException {   
//        try {             
//            //UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");        
//
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException exception) {        
//            //
//        }

        if (setConnection()) {            
            CLogin cLogin = new CLogin(); 
        }        
    }
    
}
