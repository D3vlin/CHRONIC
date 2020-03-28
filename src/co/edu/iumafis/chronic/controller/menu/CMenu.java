package co.edu.iumafis.chronic.controller.menu;

import co.edu.iumafis.chronic.controller.login.CLogin;
import co.edu.iumafis.chronic.model.dao.DaoFactory;
import co.edu.iumafis.chronic.model.dao.user.UserDao;
import co.edu.iumafis.chronic.model.dao.user.UserDaoException;
import co.edu.iumafis.chronic.model.dto.user.UserDto;
import co.edu.iumafis.chronic.view.menu.UIMenu;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 * Main Menu Controller.
 * 
 * Load the options of user
 *  
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public final class CMenu {
    
    private UIMenu window;
    
    /**
     * Empty Constructor,
     */
    public CMenu() {
        try {
            UserDao dao = DaoFactory.createUserDao();
            UserDto user = dao.findWhereUserIdEquals(UIMenu.idUser)[0];

            if (user != null) { window = new UIMenu(this, user); }
            else { JOptionPane.showMessageDialog(null, "Error al cargar el menú", "Error", JOptionPane.ERROR_MESSAGE, null); }
        
        } catch (UserDaoException | HeadlessException exception) { }        
    }
    
    /**
     * Constructor.
     * 
     * @param user 
     */
    public CMenu(UserDto user) { 
        window = new UIMenu(this, user);
    }
    
    /**
     * Upload the user information logged in and enable the functions according to your profile.
     * 
     * @param user 
     */
    public void upload(UserDto user) {
        UIMenu.idUser = user.getIdUser();
    }

    /**
     * Close the current user session and return to the login form.
     */
    public void logOut() {
        CLogin cLogin = new CLogin();
        window.dispose();
    }

    /**
     * Show the form.
     */
    public void showForm() {
        window.setVisible(true);
    }    
}