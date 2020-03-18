package co.edu.iumafis.chronic.controller.ini;

import co.edu.iumafis.chronic.controller.login.CLogin;

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
     */
    public static void main(String[] args) { ChronicIni chronicIni = new ChronicIni(); }
    
    /**
     * Main constructor, initializes the plaf JTattoo library.
     */
    public ChronicIni() {   
//        try {             
//            //UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");        
//
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException exception) {        
//            //
//        }

//        if (setConnection()) {             
//            LOG.info("HulkStore run");
            CLogin cLogin = new CLogin(); 
//        }        
    }
    
}
