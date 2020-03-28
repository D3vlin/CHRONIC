package co.edu.iumafis.chronic.model.dao.user;

import co.edu.iumafis.chronic.model.dao.DaoException;

/**
 * This class controls possible exceptions with user table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public final class UserDaoException extends DaoException
{
    /**
     * Throw an error message.
     * 
     * @param message
     */
    public UserDaoException(String message) { super(message); }

    /**
     * Throw an error message and its cause.
     * 
     * @param message
     * @param cause
     */
    public UserDaoException(String message, Throwable cause) { super(message, cause); }
}