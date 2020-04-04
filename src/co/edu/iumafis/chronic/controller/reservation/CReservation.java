package co.edu.iumafis.chronic.controller.reservation;

import co.edu.iumafis.chronic.controller.menu.CMenu;
import co.edu.iumafis.chronic.model.dao.DaoFactory;
import co.edu.iumafis.chronic.model.dao.reservation.ReservationDao;
import co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException;
import co.edu.iumafis.chronic.model.dto.reservation.ReservationDto;
import co.edu.iumafis.chronic.view.reservation.UIReservation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Main Reservation Controller.
 * 
 * Load the options of reservation
 *  
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public class CReservation {
    
    private final UIReservation window;
    
    /**
     * Empty Constructor.
     */
    public CReservation() {  
        window = new UIReservation(this);
    }    
    
    /**
     * Upload the Id to the form.
     * 
     * @param txtID
     */
    public void upload(JTextField txtID) {    
        
        findNextId(txtID);
    }
    
    /**
     * Find the Id to the form.
     * 
     * @param txtID
     */
    public void findNextId(JTextField txtID) {        
        
        try {
            ReservationDao dao = DaoFactory.createReservationDao();
            txtID.setText(dao.findNextId());
            
        } catch (ReservationDaoException exception) {}
    }
    
    /**
     * Register the new reservation.
     * 
     * @param txtID
     * @param type
     * @param document
     * @param full_name
     * @param age
     * @param quantity
     * @param totalValue
     */
    public void reserve(JTextField txtID, JComboBox type, JTextField document, JTextField full_name, JTextField age, JTextField quantity, JLabel totalValue) {
        
        if(validFields(document, full_name, age, quantity)) {
            try {
                ReservationDto dto = new ReservationDto(type.getSelectedItem().toString(), document.getText(), full_name.getText(), age.getText(), quantity.getText());
                ReservationDao dao = DaoFactory.createReservationDao();

                if(!dao.insert(dto).isIdNull()){
                    JOptionPane.showMessageDialog(null, "Se ha agregado el registro nuevo", "INSERCION", JOptionPane.INFORMATION_MESSAGE);
                    cleanFields(txtID, document, full_name, age, quantity, totalValue);

                } else { JOptionPane.showMessageDialog(null, "No se registro", "ERROR", JOptionPane.ERROR_MESSAGE); }

            } catch (ReservationDaoException exception) {}
        }
    }
    
    /**
     * Update the reservation.
     * 
     * @param txtID
     * @param type
     * @param document
     * @param full_name
     * @param age
     * @param quantity
     * @param totalValue
     * @return boolean
     */
    public boolean update(JTextField txtID, JComboBox type, JTextField document, JTextField full_name, JTextField age, JTextField quantity, JLabel totalValue) {
        
        if(validFields(document, full_name, age, quantity)) {
            try {
                ReservationDto dto = new ReservationDto(Integer.parseInt(txtID.getText()), type.getSelectedItem().toString(), document.getText(), full_name.getText(), age.getText(), quantity.getText());
                ReservationDao dao = DaoFactory.createReservationDao();

                if(dao.update(dto.createPk(), dto)){
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro", "ACTUALIZAR", JOptionPane.INFORMATION_MESSAGE);
                    cleanFields(txtID, document, full_name, age, quantity, totalValue);
                    return true;
                }
                else {
                    JOptionPane.showMessageDialog(null, "No se modifico", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            } catch (ReservationDaoException exception) { return false; }
        }
        else {
            return false;
        }
    }
    
    /**
     * Delete the reservation.
     * 
     * @param txtID
     * @return boolean
     */
    public boolean delete(JTextField txtID) {
        
        try {
            ReservationDto dto = new ReservationDto(Integer.parseInt(txtID.getText()));
            ReservationDao dao = DaoFactory.createReservationDao();

            if(dao.delete(dto.createPk())){
                JOptionPane.showMessageDialog(null, "Se ha eliminado el registro", "ACTUALIZAR", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null, "No se elimino", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (ReservationDaoException exception) { return false; }
    }
    
    /**
     * Search reservation.
     * 
     * @param txtID
     * @param type
     * @param document
     * @param full_name
     * @param age
     * @param quantity
     * @param totalValue
     * @param value
     * @return boolean
     */
    public boolean search(JTextField txtID, JComboBox type, JTextField document, JTextField full_name, JTextField age, JTextField quantity, JLabel totalValue, JLabel value) {
        
        if(txtID.getText().equals("")) {
            
            JOptionPane.showMessageDialog(null, "Debe diligenciar un código de reserva", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            try {
                ReservationDao dao = DaoFactory.createReservationDao();
                ReservationDto dto = dao.findByPrimaryKey(Integer.parseInt(txtID.getText()));

                if(dto != null){
                    type.setSelectedItem(dto.getType());
                    document.setText(dto.getDocument());
                    full_name.setText(dto.getFull_name());
                    age.setText(dto.getAge());
                    quantity.setText(dto.getQuantity());
                    calculateValue(type.getSelectedItem().toString(), quantity, value, totalValue);
                    return true;

                } else { 
                    JOptionPane.showMessageDialog(null, "No se encuentra esta reserva", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            } catch (ReservationDaoException exception) { return false; }        
        }
    }
    
    public void calculateValue(String type, JTextField quantity, JLabel value, JLabel totalValue) {
        value(type, value);
        if(!quantity.getText().equals("")) {
            if(isNumeric(quantity.getText())) {
                if(type.equals("General")) {
                    totalValue.setText(String.valueOf(Integer.parseInt(quantity.getText()) * 6500));
                }
                else {
                    totalValue.setText(String.valueOf(Integer.parseInt(quantity.getText()) * 10000));
                }
            }
            else {
                quantity.setText("");
                totalValue.setText("0");
            }
        }
        else {
            totalValue.setText("0");
        }
    }
    
    private void value(String type, JLabel value) {
        if(type.equals("General")) {
            value.setText("6500");
        }
        else {
            value.setText("10000");
        }
    }
    
    public boolean isNumeric(String cadena) {
        if(!cadena.equals("")) {
            try {
                Integer.parseInt(cadena);
                return true;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Debe ingresar números", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        else {
            return true;
        }
    }
    
    private boolean validFields(JTextField document, JTextField full_name, JTextField age, JTextField quantity) {
        if(document.getText().equals("") || full_name.getText().equals("") || age.getText().equals("") || quantity.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe diligenciar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }
    
    public void cleanFields(JTextField txtID, JTextField document, JTextField full_name, JTextField age, JTextField quantity, JLabel totalValue) {
        try {
            ReservationDao dao = DaoFactory.createReservationDao();
            document.setText("");
            full_name.setText("");
            age.setText("");
            quantity.setText("");
            totalValue.setText("0");
            txtID.setText(dao.findNextId());
        } catch (ReservationDaoException ex) {
            Logger.getLogger(CReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void back() {
        CMenu cMenu = new CMenu();
        window.dispose();
    }
}
