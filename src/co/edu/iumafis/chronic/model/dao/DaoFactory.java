package co.edu.iumafis.chronic.model.dao;

import co.edu.iumafis.chronic.model.dao.reservation.ReservationDao;
import co.edu.iumafis.chronic.model.dao.user.UserDao;
import java.sql.Connection;

/**
 * This class is responsible for creating DAO instances.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public final class DaoFactory {
    
    /**
     * createUsersDao.
     * 
     * @return UsersDao
     */
    public static UserDao createUserDao() { return new UserDao(); }

    /**
     * createUsersDao.
     * 
     * @param conn
     * @return UsersDao
     */
    public static UserDao createUsersDao(Connection conn) { return new UserDao( conn ); }
    
    /**
     * createReservationDao.
     * 
     * @return ReservationDao
     */
    public static ReservationDao createReservationDao() { return new ReservationDao(); }

    /**
     * createReservationDao.
     * 
     * @param conn
     * @return ReservationDao
     */
    public static ReservationDao createReservationDao(Connection conn) { return new ReservationDao( conn ); }
}