package co.edu.iumafis.chronic.model.dao.reservation;

import co.edu.iumafis.chronic.model.dao.DaoException;

/**
 * This class controls possible exceptions with reservation table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public final class ReservationDaoException extends DaoException {
    
    /**
     * Throw an error message.
     * 
     * @param message
     */
    public ReservationDaoException(String message) { super(message); }

    /**
     * Throw an error message and its cause.
     * 
     * @param message
     * @param cause
     */
    public ReservationDaoException(String message, Throwable cause) { super(message, cause); }
}
