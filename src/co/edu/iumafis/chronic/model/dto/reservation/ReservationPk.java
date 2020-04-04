package co.edu.iumafis.chronic.model.dto.reservation;

import java.io.Serializable;

/** 
 * This class represents the primary key of the reservation table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public class ReservationPk implements Serializable {
    
    protected int id;

    /** 
     * This attribute represents whether the primitive attribute id is null.
     */
    protected boolean idNull;

    /** 
     * Sets the value of id.
     * 
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /** 
     * Gets the value of id.
     * 
     * @return int
     */
    public int getId() { return id; }   

    /**
     * Empty Constructor.
     */
    public ReservationPk() {}

    /**
     * Constructor.
     * 
     * @param id
     */
    public ReservationPk(final int id) {
        
        this.id = id;
        this.idNull = false;
    } 

    /** 
     * Sets the value of idNull.
     * 
     * @param idNull
     */
    public void setIdNull(boolean idNull) { this.idNull = idNull; }

    /** 
     * Gets the value of idNull.
     * 
     * @return boolean
     */
    public boolean isIdNull() { return idNull; }
}
