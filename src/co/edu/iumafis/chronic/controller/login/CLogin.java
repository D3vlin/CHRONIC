package co.edu.iumafis.chronic.controller.login;

import co.edu.iumafis.chronic.controller.menu.CMenu;
import co.edu.iumafis.chronic.model.dao.DaoFactory;
import co.edu.iumafis.chronic.model.dao.user.UserDao;
import co.edu.iumafis.chronic.model.dao.user.UserDaoException;
import co.edu.iumafis.chronic.model.dto.user.UserDto;
import co.edu.iumafis.chronic.view.login.UILogin;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
    
    private int entryAttempts;
    private final UILogin window;
    
    /**
     * Empty Constructor.
     */
    public CLogin() {        
        this.window = new UILogin(this);
    }
    
    /**
     * Valid if the user's credentials are valid.
     * 
     * @param txtUser
     * @param pwdPass 
     */
    public void validate(JTextField txtUser, JPasswordField pwdPass) {        
        try {
            UserDao dao = DaoFactory.createUserDao();
            UserDto user = dao.validateUser(txtUser.getText(), pwdPass.getText());
            
            if (user != null) {
                window.dispose();
                CMenu cMenu = new CMenu(user);
            
            } else {
                entryAttempts++;
                JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE, null);
                
                if(entryAttempts >= 3) {
                    JOptionPane.showMessageDialog(null, "A superado el número de intentos", "Info", JOptionPane.INFORMATION_MESSAGE, null);
                    close();
                }
            }
        
        } catch (UserDaoException | HeadlessException exception) {
            //
        }
    }
    
    /**
     * Close the form and finish the application.
     */
    public void close() { 
        window.dispose(); 
    }
}
