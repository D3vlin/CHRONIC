package co.edu.iumafis.chronic.model.dto.reservation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the reservation model.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public final class ReservationDto implements Serializable {
    
    /** 
     * This attribute maps to the column id in the reservation table.
     */
    protected int id;    

    /** 
     * This attribute maps to the column full_name in the reservation table.
     */
    protected String full_name;

    /** 
     * This attribute maps to the column document in the reservation table.
     */
    protected String document;

    /** 
     * This attribute maps to the column age in the reservation table.
     */
    protected String age;

    /** 
     * This attribute maps to the column type in the reservation table.
     */
    protected String type;

    /** 
     * This attribute maps to the column quantity in the reservation table.
     */
    protected String quantity;
    
    /** 
     * This attribute maps to the column total_value in the reservation table.
     */
    protected int total_value;

    /** 
     * This attribute maps to the column date_reservation in the reservation table.
     */
    protected String date_reservation;

    /**
     * Empty Constructor. 
     */
    public ReservationDto() { }

    /**
     * Constructor.
     * 
     * @param id
     */ 
    public ReservationDto(int id) {
        this.id = id;
    }

    /**
     * Constructor.
     * 
     * @param type
     * @param full_name
     * @param document
     * @param age
     * @param quantity
     */ 
    public ReservationDto(String type, String document, String full_name, String age, String quantity) {
        this.type = type;
        this.document = document;
        this.full_name = full_name;
        this.age = age;
        this.quantity = quantity;        
        this.date_reservation = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        if(type.equals("General")) {
            this.total_value = Integer.parseInt(quantity) * 6500;
        }
        else {
            this.total_value = Integer.parseInt(quantity) * 11000;
        }
    }

    /**
     * Constructor.
     * 
     * @param id
     * @param type
     * @param full_name
     * @param document
     * @param age
     * @param quantity
     */ 
    public ReservationDto(int id, String type, String document, String full_name, String age, String quantity) {
        this(type, document, full_name, age, quantity);
        this.id = id;
    }

    /**
     * Gets the value of id.
     * 
     * @return int
     */
    public int getId() { return id; }

    /**
     * Sets the value of password.
     * 
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Gets the value of Full_name.
     * 
     * @return String
     */
    public String getFull_name() { return full_name; }

    /**
     * Sets the value of full_name.
     * 
     * @param full_name
     */
    public void setFull_name(String full_name) { this.full_name = full_name; }

    /**
     * Gets the value of document.
     * 
     * @return String
     */
    public String getDocument() { return document; }

    /**
     * Sets the value of document.
     * 
     * @param document
     */
    public void setDocument(String document) { this.document = document; }

    /**
     * Gets the value of age.
     * 
     * @return String
     */
    public String getAge() { return age; }

    /**
     * Sets the value of age.
     * 
     * @param age
     */
    public void setAge(String age) { this.age = age; }

    /**
     * Gets the value of type.
     * 
     * @return String
     */
    public String getType() { return type; }

    /**
     * Sets the value of type.
     * 
     * @param type
     */
    public void setType(String type) { this.type = type; }

    /**
     * Gets the value of quantity.
     * 
     * @return String
     */
    public String getQuantity() { return quantity; }

    /**
     * Sets the value of quantity.
     * 
     * @param quantity
     */
    public void setQuantity(String quantity) { this.quantity = quantity; }

    /**
     * Gets the value of total_value.
     * 
     * @return int
     */
    public int getTotal_value() { return total_value; }

    /**
     * Sets the value of total_value.
     * 
     * @param total_value
     */
    public void setTotal_value(int total_value) { this.total_value = total_value; }

    /**
     * Gets the value of date_reservation.
     * 
     * @return String
     */
    public String getDate_reservation() { return date_reservation; }

    /**
     * Sets the value of date_reservation.
     * 
     * @param date_reservation
     */
    public void setDate_reservation(String date_reservation) { this.date_reservation = date_reservation; }

    /**
     * Method 'createPk'
     * 
     * @return ReservationPk
     */
    public ReservationPk createPk() { return new ReservationPk(id); }
}
