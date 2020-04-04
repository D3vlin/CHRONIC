package co.edu.iumafis.chronic.model.dao.reservation;

import co.edu.iumafis.chronic.model.dao.AbstractDao;
import co.edu.iumafis.chronic.model.dao.ResourceManager;
import co.edu.iumafis.chronic.model.dto.reservation.ReservationDto;
import co.edu.iumafis.chronic.model.dto.reservation.ReservationPk;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class handles queries to the reservation table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public final class ReservationDao extends AbstractDao {
    
    /** 
     * The factory class for this DAO has two versions of the create() method - one that
     * takes no arguments and one that takes a Connection argument. If the Connection version
     * is chosen then the connection will be stored in this attribute and will be used by all
     * calls to this DAO, otherwise a new Connection will be allocated for each operation.
     */
    protected java.sql.Connection userConn;

    /** 
     * All finder methods in this class use this SELECT constant to build their queries.
     */
    protected final String SQL_SELECT = "SELECT * FROM " + getTableName() + "";
    
    /** 
     * Finder methods will pass this value to the JDBC setMaxRows method.
     */
    protected int maxRows;
    
    /** 
     * SQL INSERT statement for this table.
     */
    protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( full_name, document, age, type, quantity, total_value, date_reservation ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    
    /** 
     * SQL UPDATE statement for this table.
     */
    protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET full_name = ?, document = ?, age = ?, type = ?, quantity = ?, total_value = ?, date_reservation = ? WHERE id = ?";
    
    /** 
     * SQL DELETE statement for this table
     */
    protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE id = ?";
    
    /** 
     * Indexes of the columns in the user table.
     */
    protected static final int COLUMN_ID = 1;
    protected static final int COLUMN_FULL_NAME = 2;
    protected static final int COLUMN_DOCUMENT = 3;
    protected static final int COLUMN_AGE = 4;
    protected static final int COLUMN_TYPE = 5;
    protected static final int COLUMN_QUANTITY = 6;
    protected static final int COLUMN_TOTAL_VALUE = 7;
    protected static final int COLUMN_DATE_RESERVATION = 8;
    
    /** 
     * Number of columns.
     */
    protected static final int NUMBER_OF_COLUMNS = 8;
    
    /** 
     * Index of primary-key column userId
     */
    protected static final int PK_COLUMN_ID = 1;
    
    /** 
     * Inserts a new row in the reservation table.
     * 
     * @param reservationDto
     * @return ReservationPk
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public ReservationPk insert(ReservationDto reservationDto) throws ReservationDaoException {
        long t1 = System.currentTimeMillis();
        // declare variables
        final boolean isConnSupplied = (userConn != null);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;

        try {
            // get the user-specified connection or get a connection from the ResourceManager
            connection = isConnSupplied ? userConn : ResourceManager.getConnection();

            statement = connection.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );
            int index = 1;
            statement.setString( index++, reservationDto.getFull_name() );
            statement.setString( index++, reservationDto.getDocument() );
            statement.setString( index++, reservationDto.getAge() );
            statement.setString( index++, reservationDto.getType());
            statement.setString( index++, reservationDto.getQuantity());
            statement.setInt( index++, reservationDto.getTotal_value());
            statement.setString( index++, reservationDto.getDate_reservation());
            System.out.println( "Executing " + SQL_INSERT + " with DTO: " + reservationDto );
            int rows = statement.executeUpdate();
            long t2 = System.currentTimeMillis();
            System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );

            // retrieve values from auto-increment columns
            resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                    reservationDto.setId( resultSet.getInt( 1 ) );
            }

            reset(reservationDto);
            return reservationDto.createPk();
            
        } catch (Exception exception) { throw new ReservationDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
    }
    
    /** 
     * Updates a single row in the resewrvation table.
     * 
     * @param reservationPk
     * @param reservationDto
     * @return boolean
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public boolean update(ReservationPk reservationPk, ReservationDto reservationDto) throws ReservationDaoException {
        // declare variables
        long t1 = System.currentTimeMillis();
        final boolean isConnSupplied = (userConn != null);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // get the user-specified connection or get a connection from the ResourceManager
            connection = isConnSupplied ? userConn : ResourceManager.getConnection();

            System.out.println( "Executing " + SQL_UPDATE + " with DTO: " + reservationDto );
            statement = connection.prepareStatement( SQL_UPDATE );
            int index=1;
            statement.setString( index++, reservationDto.getFull_name() );
            statement.setString( index++, reservationDto.getDocument() );
            statement.setString( index++, reservationDto.getAge() );
            statement.setString( index++, reservationDto.getType() );
            statement.setString( index++, reservationDto.getQuantity() );
            statement.setInt( index++, reservationDto.getTotal_value() );
            statement.setString( index++, reservationDto.getDate_reservation() );
            System.out.println(reservationPk.getId());
            statement.setInt( index++, reservationDto.getId() );
            int rows = statement.executeUpdate();
            reset(reservationDto);
            long t2 = System.currentTimeMillis();
            System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
            
            return rows > 0;
            
        } catch (SQLException exception) { throw new ReservationDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
    }
    
    /** 
     * Deletes a single row in the reservation table.
     * 
     * @param reservationPk
     * @return boolean
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public boolean delete(ReservationPk reservationPk) throws ReservationDaoException {
        // declare variables
        long t1 = System.currentTimeMillis();
        final boolean isConnSupplied = (userConn != null);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // get the user-specified connection or get a connection from the ResourceManager
            connection = isConnSupplied ? userConn : ResourceManager.getConnection();

            System.out.println( "Executing " + SQL_DELETE + " with PK: " + reservationPk );
            statement = connection.prepareStatement( SQL_DELETE );
            statement.setInt( 1, reservationPk.getId() );
            int rows = statement.executeUpdate();
            long t2 = System.currentTimeMillis();
            System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
            
            return rows > 0;
        
        } catch (SQLException exception) { throw new ReservationDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
   }
    
    /** 
     * Returns the rows from the reservation table that matches the specified primary-key value.
     * 
     * @param reservationPk
     * @return ReservationDto
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public ReservationDto findByPrimaryKey(ReservationPk reservationPk) throws ReservationDaoException {
        return findByPrimaryKey( reservationPk.getId() );
    }
    
    /** 
     * Returns all rows from the reservation table that match the criteria 'id = :id'.
     * 
     * @param id
     * @return ReservationDto
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public ReservationDto findByPrimaryKey(int id) throws ReservationDaoException {
        ReservationDto ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {id} );
        return ret.length==0 ? null : ret[0];
    }
    
    /** 
     * Returns all rows from the reservation table that match the criteria ''.
     * 
     * @return ReservationDto[]
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public ReservationDto[] findAll() throws ReservationDaoException {
        return findByDynamicSelect( SQL_SELECT + " ORDER BY id", null );
    }
    
    /** 
     * Returns all rows from the reservation table that match the criteria 'id = :id'.
     * 
     * @param id
     * @return ReservationDto[]
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public ReservationDto[] findWhereUserIdEquals(int id) throws ReservationDaoException {
        return findByDynamicSelect( SQL_SELECT + " WHERE id = ? ORDER BY id", new Object[] {id} );
    }
    
    /**
     * Empty Constructor.
     */
    public ReservationDao() {}

    /**
     * Constructor.
     * 
     * @param userConn
     */
    public ReservationDao(final java.sql.Connection userConn) { this.userConn = userConn; }
    
    /** 
     * Sets the value of maxRows.
     * 
     * @param maxRows
     */
    public void setMaxRows(int maxRows) { this.maxRows = maxRows; }

    /** 
     * Gets the value of maxRows.
     * 
     * @return int
     */
    public int getMaxRows() { return maxRows; }

    /**
     * Returns the name of the table.
     * 
     * @return String
     */
    public String getTableName() { return "bd_cinemachronic.reservation"; }
    
    /** 
     * Fetches a single row from the result set.
     * 
     * @param resultSet
     * @return ReservationDto[]
     * @throws java.sql.SQLException
     */
    protected ReservationDto fetchSingleResult(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            ReservationDto reservationDto = new ReservationDto();
            populateDto( reservationDto, resultSet);
            return reservationDto;

        } else { return null; }		
    }
    
    /** 
     * Fetches multiple rows from the result set.
     * 
     * @param resultSet
     * @return ReservationDto[]
     * @throws java.sql.SQLException
     */
    protected ReservationDto[] fetchMultiResults(ResultSet resultSet) throws SQLException {
        Collection resultList = new ArrayList();
        while (resultSet.next()) {
            ReservationDto reservationDto = new ReservationDto();
            populateDto( reservationDto, resultSet);
            resultList.add( reservationDto );
        }

        ReservationDto ret[] = new ReservationDto[ resultList.size() ];
        resultList.toArray( ret );
        return ret;
    }
    
    /** 
     * Populates a DTO with data from a ResultSet.
     * 
     * @param reservationDto
     * @param resultSet
     * @throws java.sql.SQLException
     */
    protected void populateDto(ReservationDto reservationDto, ResultSet resultSet) throws SQLException {
        reservationDto.setId( resultSet.getInt( COLUMN_ID ) );
        reservationDto.setFull_name(resultSet.getString( COLUMN_FULL_NAME ) );
        reservationDto.setDocument(resultSet.getString( COLUMN_DOCUMENT ) );
        reservationDto.setAge(resultSet.getString( COLUMN_AGE ) );
        reservationDto.setType(resultSet.getString( COLUMN_TYPE ) );
        reservationDto.setQuantity(resultSet.getString( COLUMN_QUANTITY ) );
        reservationDto.setTotal_value(resultSet.getInt( COLUMN_TOTAL_VALUE ) );
        reservationDto.setDate_reservation(resultSet.getString( COLUMN_DATE_RESERVATION ) );
    }
    
    /** 
     * Resets the modified attributes in the DTO.
     * 
     * @param reservationDto
     */
    protected void reset(ReservationDto reservationDto) {}
    
    /** 
     * Returns all rows from the reservation table that match the specified arbitrary SQL statement.
     * 
     * @param sql
     * @param sqlParams
     * @return ReservationDto[]
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public ReservationDto[] findByDynamicSelect(String sql, Object[] sqlParams) throws ReservationDaoException {
        // declare variables
        final boolean isConnSupplied = (userConn != null);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // get the user-specified connection or get a connection from the ResourceManager
            connection = isConnSupplied ? userConn : ResourceManager.getConnection();

            System.out.println( "Executing " + sql );
            // prepare statement
            statement = connection.prepareStatement( sql );
            statement.setMaxRows( maxRows );

            // bind parameters
            for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
                statement.setObject( i+1, sqlParams[i] );
            }

            resultSet = statement.executeQuery();

            // fetch the results
            return fetchMultiResults(resultSet);
            
        } catch (Exception exception) { throw new ReservationDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(resultSet);
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
    }
    
    /** 
     * Returns all rows from the users table that match the specified arbitrary SQL statement.
     * 
     * @param sql
     * @param sqlParams
     * @return ReservationDto[]
     * @throws co.edu.iumafis.chronic.model.dao.reservation.ReservationDaoException
     */
    public ReservationDto[] findByDynamicWhere(String sql, Object[] sqlParams) throws ReservationDaoException {
        // declare variables
        final boolean isConnSupplied = (userConn != null);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // get the user-specified connection or get a connection from the ResourceManager
            connection = isConnSupplied ? userConn : ResourceManager.getConnection();

            // construct the SQL statement
            final String SQL = SQL_SELECT + " WHERE " + sql;
            
            // prepare statement
            statement = connection.prepareStatement( SQL );
            statement.setMaxRows( maxRows );

            // bind parameters
            for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
                statement.setObject( i+1, sqlParams[i] );
            }

            resultSet = statement.executeQuery();

            // fetch the results
            return fetchMultiResults(resultSet);
        
        } catch (Exception exception) { throw new ReservationDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(resultSet);
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
    }
    
    /**
     * Returns the next available id for the next reservation.
     * 
     * @return String
     * @throws ReservationDaoException 
     */
    public String findNextId() throws ReservationDaoException {
        // declare variables
        final boolean isConnSupplied = (userConn != null);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // get the user-specified connection or get a connection from the ResourceManager
            conn = isConnSupplied ? userConn : ResourceManager.getConnection();
            
            // construct the SQL statement
            final String SQL = "SELECT `AUTO_INCREMENT`\n" +
                                "FROM  INFORMATION_SCHEMA.TABLES\n" +
                                "WHERE TABLE_SCHEMA = 'bd_cinemachronic'\n" +
                                "AND TABLE_NAME   = 'reservation';";
            
            System.out.println( "Executing " + SQL);
            stmt = conn.prepareStatement( SQL );

            rs = stmt.executeQuery();
            rs.next();
            
            return rs.getString(1);
        
        } catch (Exception exception) { throw new ReservationDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(rs);
            ResourceManager.close(stmt);
            if (!isConnSupplied) { ResourceManager.close(conn); }
        }
    }
}
