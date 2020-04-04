package co.edu.iumafis.chronic.model.dto.sale;

/** 
 * This class represents the primary key of the sale table.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public class SalePk {
    
    protected int id_sale;

    /** 
     * This attribute represents whether the primitive attribute id_sale is null.
     */
    protected boolean idNull;

    /** 
     * Sets the value of id_sale.
     * 
     * @param id_sale
     */
    public void setId(int id_sale) { this.id_sale = id_sale; }

    /** 
     * Gets the value of id_sale.
     * 
     * @return int
     */
    public int getId() { return id_sale; }   

    /**
     * Empty Constructor.
     */
    public SalePk() {}

    /**
     * Constructor.
     * 
     * @param id_sale
     */
    public SalePk(final int id_sale) {
        
        this.id_sale = id_sale;
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
