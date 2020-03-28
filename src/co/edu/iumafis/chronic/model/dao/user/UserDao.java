package co.edu.iumafis.chronic.model.dao.user;

import co.edu.iumafis.chronic.model.dao.AbstractDao;
import co.edu.iumafis.chronic.model.dao.ResourceManager;
import co.edu.iumafis.chronic.model.dto.user.UserDto;
import java.sql.Connection;
import java.util.Collection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class handles queries to the user table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public final class UserDao extends AbstractDao {
    
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
    protected final String SQL_SELECT = "SELECT id_user, nickName, password FROM " + getTableName() + "";

    /** 
     * Finder methods will pass this value to the JDBC setMaxRows method.
     */
    protected int maxRows;
    
    /** 
     * Indexes of the columns in the user table.
     */
    protected static final int COLUMN_ID_USER = 1;
    protected static final int COLUMN_NICKNAME = 2;
    protected static final int COLUMN_PASSWORD = 3;

    /** 
     * Number of columns.
     */
    protected static final int NUMBER_OF_COLUMNS = 3;

    /** 
     * Index of primary-key column userId
     */
    protected static final int PK_COLUMN_ID_USER = 1;

    /** 
     * Returns all rows from the users table that match the criteria 'userId = :userId'.
     * 
     * @param userId
     * @return UserDto[]
     * @throws co.edu.iumafis.chronic.model.dao.user.UserDaoException
     */
    public UserDto[] findWhereUserIdEquals(int userId) throws UserDaoException {
        return findByDynamicSelect( SQL_SELECT + " WHERE userId = ? ORDER BY userId", new Object[] {userId} );
    }

    /**
     * Empty Constructor.
     */
    public UserDao() {}

    /**
     * Constructor.
     * 
     * @param userConn
     */
    public UserDao(final java.sql.Connection userConn) { this.userConn = userConn; }

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
    public String getTableName() { return "bd_cinemachronic.user"; }

    /** 
     * Fetches a single row from the result set.
     * 
     * @param resultSet
     * @return UserDto
     * @throws java.sql.SQLException
     */
    protected UserDto fetchSingleResult(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            UserDto userDto = new UserDto();
            populateDto( userDto, resultSet);
            return userDto;

        } else { return null; }		
    }

    /** 
     * Populates a DTO with data from a ResultSet.
     * 
     * @param userDto
     * @param resultSet
     * @throws java.sql.SQLException
     */
    protected void populateDto(UserDto userDto, ResultSet resultSet) throws SQLException {
        userDto.setIdUser( resultSet.getInt( COLUMN_ID_USER ) );
        userDto.setNickName( resultSet.getString( COLUMN_NICKNAME ) );
        userDto.setPassword( resultSet.getString( COLUMN_PASSWORD ) );
    }

    /** 
     * Resets the modified attributes in the DTO.
     * 
     * @param userDto
     */
    protected void reset(UserDto userDto) {}

    /** 
     * Returns all rows from the users table that match the specified arbitrary SQL statement.
     * 
     * @param sql
     * @param sqlParams
     * @return UsersDto[]
     * @throws co.edu.iumafis.chronic.model.dao.user.UserDaoException
     */
    public UserDto[] findByDynamicSelect(String sql, Object[] sqlParams) throws UserDaoException {
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
            
        } catch (Exception exception) { throw new UserDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(resultSet);
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
    }
    /** 
     * Fetches multiple rows from the result set.
     * 
     * @param resultSet
     * @return UserDto[]
     * @throws java.sql.SQLException
     */
    protected UserDto[] fetchMultiResults(ResultSet resultSet) throws SQLException {
        Collection resultList = new ArrayList();
        while (resultSet.next()) {
            UserDto userDto = new UserDto();
            populateDto( userDto, resultSet);
            resultList.add( userDto );
        }

        UserDto ret[] = new UserDto[ resultList.size() ];
        resultList.toArray( ret );
        return ret;
    }

    /** 
     * Returns all rows from the users table that match the specified arbitrary SQL statement.
     * 
     * @param sql
     * @param sqlParams
     * @return UsersDto[]
     * @throws co.edu.iumafis.chronic.model.dao.user.UserDaoException
     */
    public UserDto[] findByDynamicWhere(String sql, Object[] sqlParams) throws UserDaoException {
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
        
        } catch (Exception exception) { throw new UserDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(resultSet);
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
    }
    
    /**
     * Validate a user's credentials.
     * 
     * @param nickName
     * @param password
     * @return UserDto
     * @throws UserDaoException 
     */
    public UserDto validateUser(String nickName, String password) throws UserDaoException {
        UserDto rsp[] = findByDynamicWhere( "nickname = ? and password = ?", new Object[] { nickName, password } );          
        
        if (rsp.length != 0) {
            return rsp[0]; 
            
        } else {
            return null;
        }
    }
}