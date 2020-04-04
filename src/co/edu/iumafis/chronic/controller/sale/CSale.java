package co.edu.iumafis.chronic.controller.sale;

import co.edu.iumafis.chronic.controller.menu.CMenu;
import co.edu.iumafis.chronic.model.dao.DaoFactory;
import co.edu.iumafis.chronic.model.dao.reservation.ReservationDao;
import co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException;
import co.edu.iumafis.chronic.model.dao.sale.SaleDao;
import co.edu.iumafis.chronic.model.dao.sale.SaleDaoException;
import co.edu.iumafis.chronic.model.dto.reservation.ReservationDto;
import co.edu.iumafis.chronic.model.dto.sale.SaleDto;
import co.edu.iumafis.chronic.view.sale.IUSale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Main Sale Controller.
 * 
 * Load the options of sale
 *  
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public class CSale {
    
    private final IUSale window;
    
    /**
     * Empty Constructor.
     */
    public CSale() {  
        window = new IUSale(this);
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
            SaleDao dao = DaoFactory.createSaleDao();
            txtID.setText(dao.findNextId());
            
        } catch (SaleDaoException exception) {}
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
    
    public void cleanFields(JTextField txtIdReservation, JTextField txtID, JTextField document, JTextField full_name, JTextField age, JTextField quantity, JLabel totalValue) {
        try {
            SaleDao dao = DaoFactory.createSaleDao();
            document.setText("");
            full_name.setText("");
            age.setText("");
            quantity.setText("");
            totalValue.setText("0");
            txtIdReservation.setText("");
            txtID.setText(dao.findNextId());
        } catch (SaleDaoException ex) {
            Logger.getLogger(CSale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Register the new reservation.
     * 
     * @param txtIdReservation
     * @param txtID
     * @param type
     * @param document
     * @param full_name
     * @param age
     * @param quantity
     * @param totalValue
     */
    public void buy(JTextField txtIdReservation, JTextField txtID, JComboBox type, JTextField document, JTextField full_name, JTextField age, JTextField quantity, JLabel totalValue) {
        
        try {
            SaleDto dto = new SaleDto(Integer.parseInt(txtIdReservation.getText()), type.getSelectedItem().toString(), document.getText(), full_name.getText(), age.getText(), quantity.getText());
            SaleDao dao = DaoFactory.createSaleDao();

            if(!dao.insert(dto).isIdNull()){
                JOptionPane.showMessageDialog(null, "Se ha agregado el registro nuevo", "INSERCION", JOptionPane.INFORMATION_MESSAGE);
                cleanFields(txtIdReservation, txtID, document, full_name, age, quantity, totalValue);

            } else { JOptionPane.showMessageDialog(null, "No se registro", "ERROR", JOptionPane.ERROR_MESSAGE); }

        } catch (SaleDaoException exception) {}
    }
    
    public void back() {
        CMenu cMenu = new CMenu();
        window.dispose();
    }
}
