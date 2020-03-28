package co.edu.iumafis.chronic.model.dto.user;

import java.io.Serializable;

/** 
 * This class represents the primary key of the user table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public final class UserPk implements Serializable {
    
    protected int id_user;

    /** 
     * This attribute represents whether the primitive attribute id_user is null.
     */
    protected boolean idUserNull;

    /** 
     * Sets the value of id_user.
     * 
     * @param id_user
     */
    public void setUserId(int id_user) { this.id_user = id_user; }

    /** 
     * Gets the value of id_user.
     * 
     * @return int
     */
    public int getIdUser() { return id_user; }

    /**
     * Empty Constructor.
     */
    public UserPk() {}

    /**
     * Constructor.
     * 
     * @param id_user
     */
    public UserPk(final int id_user) {
        
        this.id_user = id_user;
        this.idUserNull = false;
    }

    /** 
     * Sets the value of idUserNull.
     * 
     * @param idUserNull
     */
    public void setIdUserNull(boolean idUserNull) { this.idUserNull = idUserNull; }

    /** 
     * Gets the value of idUserNull.
     * 
     * @return boolean
     */
    public boolean isIdUserNull() { return idUserNull; }
}