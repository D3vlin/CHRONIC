package co.edu.iumafis.chronic.controller.login;

import co.edu.iumafis.chronic.view.login.UILogin;

/**
 * Login view controller.
 * 
 * Validates the entry of a user, verifying its existence in the database.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-17
 */
public class CLogin {
    
    private final UILogin window;
    
    /**
     * Empty Constructor.
     */
    public CLogin() {        
        this.window = new UILogin(this);
    }
}
