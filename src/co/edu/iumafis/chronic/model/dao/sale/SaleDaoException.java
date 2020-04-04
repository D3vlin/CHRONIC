package co.edu.iumafis.chronic.model.dao.sale;

import co.edu.iumafis.chronic.model.dao.DaoException;

/**
 * This class controls possible exceptions with sale table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public final class SaleDaoException extends DaoException  {
    
    /**
     * Throw an error message.
     * 
     * @param message
     */
    public SaleDaoException(String message) { super(message); }

    /**
     * Throw an error message and its cause.
     * 
     * @param message
     * @param cause
     */
    public SaleDaoException(String message, Throwable cause) { super(message, cause); }
}
