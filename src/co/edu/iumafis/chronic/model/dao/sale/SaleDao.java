package co.edu.iumafis.chronic.model.dao.sale;

import co.edu.iumafis.chronic.model.dao.ResourceManager;
import co.edu.iumafis.chronic.model.dto.sale.SaleDto;
import co.edu.iumafis.chronic.model.dto.sale.SalePk;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class handles queries to the sale table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public class SaleDao {
    
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
    protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( id, full_name, document, age, type, quantity, total_value, date_sale ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
    
    /** 
     * Indexes of the columns in the user table.
     */
    protected static final int COLUMN_ID_SALE = 1;
    protected static final int COLUMN_ID = 2;
    protected static final int COLUMN_FULL_NAME = 3;
    protected static final int COLUMN_DOCUMENT = 4;
    protected static final int COLUMN_AGE = 5;
    protected static final int COLUMN_TYPE = 6;
    protected static final int COLUMN_QUANTITY = 7;
    protected static final int COLUMN_TOTAL_VALUE = 8;
    protected static final int COLUMN_DATE_SALE = 9;
    
    /** 
     * Number of columns.
     */
    protected static final int NUMBER_OF_COLUMNS = 9;
    
    /** 
     * Index of primary-key column userId
     */
    protected static final int PK_COLUMN_ID_SALE = 1;
    
    /** 
     * Inserts a new row in the reservation table.
     * 
     * @param saleDto
     * @return ReservationPk
     * @throws co.edu.iumafis.chronic.model.dao.sale.SaleDaoException
     */
    public SalePk insert(SaleDto saleDto) throws SaleDaoException {
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
            statement.setInt( index++, saleDto.getId());
            statement.setString( index++, saleDto.getFull_name() );
            statement.setString( index++, saleDto.getDocument() );
            statement.setString( index++, saleDto.getAge() );
            statement.setString( index++, saleDto.getType());
            statement.setString( index++, saleDto.getQuantity());
            statement.setInt( index++, saleDto.getTotal_value());
            statement.setString( index++, saleDto.getDate_sale());
            System.out.println( "Executing " + SQL_INSERT + " with DTO: " + saleDto );
            int rows = statement.executeUpdate();
            long t2 = System.currentTimeMillis();
            System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );

            // retrieve values from auto-increment columns
            resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                    saleDto.setId( resultSet.getInt( 1 ) );
            }

            reset(saleDto);
            return saleDto.createPk();
            
        } catch (Exception exception) { throw new SaleDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(statement);
            if (!isConnSupplied) { ResourceManager.close(connection); }
        }
    }
    
    /** 
     * Returns the rows from the reservation table that matches the specified primary-key value.
     * 
     * @param salePk
     * @return ReservationDto
     * @throws co.edu.iumafis.chronic.model.dao.sale.SaleDaoException
     */
    public SaleDto findByPrimaryKey(SalePk salePk) throws SaleDaoException {
        return findByPrimaryKey( salePk.getId() );
    }
    
    /** 
     * Returns all rows from the reservation table that match the criteria 'id = :id'.
     * 
     * @param id
     * @return ReservationDto
     * @throws co.edu.iumafis.chronic.model.dao.sale.SaleDaoException
     */
    public SaleDto findByPrimaryKey(int id) throws SaleDaoException {
        SaleDto ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {id} );
        return ret.length==0 ? null : ret[0];
    }
    
    /**
     * Empty Constructor.
     */
    public SaleDao() {}

    /**
     * Constructor.
     * 
     * @param userConn
     */
    public SaleDao(final java.sql.Connection userConn) { this.userConn = userConn; }
    
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
    public String getTableName() { return "bd_cinemachronic.ticketsale"; }
    
    /** 
     * Fetches a single row from the result set.
     * 
     * @param resultSet
     * @return ReservationDto[]
     * @throws java.sql.SQLException
     */
    protected SaleDto fetchSingleResult(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            SaleDto reservationDto = new SaleDto();
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
    protected SaleDto[] fetchMultiResults(ResultSet resultSet) throws SQLException {
        Collection resultList = new ArrayList();
        while (resultSet.next()) {
            SaleDto reservationDto = new SaleDto();
            populateDto( reservationDto, resultSet);
            resultList.add( reservationDto );
        }

        SaleDto ret[] = new SaleDto[ resultList.size() ];
        resultList.toArray( ret );
        return ret;
    }
    
    /** 
     * Populates a DTO with data from a ResultSet.
     * 
     * @param saleDto
     * @param resultSet
     * @throws java.sql.SQLException
     */
    protected void populateDto(SaleDto saleDto, ResultSet resultSet) throws SQLException {
        saleDto.setId_sale(resultSet.getInt( COLUMN_ID_SALE ) );
        saleDto.setId( resultSet.getInt( COLUMN_ID ) );
        saleDto.setFull_name(resultSet.getString( COLUMN_FULL_NAME ) );
        saleDto.setDocument(resultSet.getString( COLUMN_DOCUMENT ) );
        saleDto.setAge(resultSet.getString( COLUMN_AGE ) );
        saleDto.setType(resultSet.getString( COLUMN_TYPE ) );
        saleDto.setQuantity(resultSet.getString( COLUMN_QUANTITY ) );
        saleDto.setTotal_value(resultSet.getInt( COLUMN_TOTAL_VALUE ) );
        saleDto.setDate_sale(resultSet.getString( COLUMN_DATE_SALE ) );
    }
    
    /** 
     * Resets the modified attributes in the DTO.
     * 
     * @param saleDto
     */
    protected void reset(SaleDto saleDto) {}
    
    /** 
     * Returns all rows from the reservation table that match the specified arbitrary SQL statement.
     * 
     * @param sql
     * @param sqlParams
     * @return ReservationDto[]
     * @throws co.edu.iumafis.chronic.model.dao.sale.SaleDaoException
     */
    public SaleDto[] findByDynamicSelect(String sql, Object[] sqlParams) throws SaleDaoException {
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
            
        } catch (Exception exception) { throw new SaleDaoException( "Exception: " + exception.getMessage(), exception );
        
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
     * @throws co.edu.iumafis.chronic.model.dao.sale.SaleDaoException 
     */
    public String findNextId() throws SaleDaoException {
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
                                "AND TABLE_NAME   = 'ticketsale';";
            
            System.out.println( "Executing " + SQL);
            stmt = conn.prepareStatement( SQL );

            rs = stmt.executeQuery();
            rs.next();
            
            return rs.getString(1);
        
        } catch (Exception exception) { throw new SaleDaoException( "Exception: " + exception.getMessage(), exception );
        
        } finally {
            ResourceManager.close(rs);
            ResourceManager.close(stmt);
            if (!isConnSupplied) { ResourceManager.close(conn); }
        }
    }
}
