package co.edu.iumafis.chronic.model.dto.sale;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the sale model.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-29
 */
public class SaleDto implements Serializable {
    
    /** 
     * This attribute maps to the column id in the sale table.
     */
    protected int id_sale;    
    
    /** 
     * This attribute maps to the column id in the sale table.
     */
    protected int id;    

    /** 
     * This attribute maps to the column full_name in the sale table.
     */
    protected String full_name;

    /** 
     * This attribute maps to the column document in the sale table.
     */
    protected String document;

    /** 
     * This attribute maps to the column age in the sale table.
     */
    protected String age;

    /** 
     * This attribute maps to the column type in the sale table.
     */
    protected String type;

    /** 
     * This attribute maps to the column quantity in the sale table.
     */
    protected String quantity;
    
    /** 
     * This attribute maps to the column total_value in the sale table.
     */
    protected int total_value;

    /** 
     * This attribute maps to the column date_sale in the sale table.
     */
    protected String date_sale;

    /**
     * Empty Constructor. 
     */
    public SaleDto() { }

    /**
     * Constructor.
     * 
     * @param id
     */ 
    public SaleDto(int id) {
        this.id = id;
    }

    /**
     * Constructor.
     * 
     * @param type
     * @param full_name
     * @param document
     * @param age
     * @param quantity
     */ 
    public SaleDto(String type, String document, String full_name, String age, String quantity) {
        this.type = type;
        this.document = document;
        this.full_name = full_name;
        this.age = age;
        this.quantity = quantity;        
        this.date_sale = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        if(type.equals("General")) {
            this.total_value = Integer.parseInt(quantity) * 6500;
        }
        else {
            this.total_value = Integer.parseInt(quantity) * 11000;
        }
    }

    /**
     * Constructor.
     * 
     * @param id
     * @param type
     * @param full_name
     * @param document
     * @param age
     * @param quantity
     */ 
    public SaleDto(int id, String type, String document, String full_name, String age, String quantity) {
        this(type, document, full_name, age, quantity);
        this.id = id;
    }

    public int getId_sale() {
        return id_sale;
    }

    public void setId_sale(int id_sale) {
        this.id_sale = id_sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getTotal_value() {
        return total_value;
    }

    public void setTotal_value(int total_value) {
        this.total_value = total_value;
    }

    public String getDate_sale() {
        return date_sale;
    }

    public void setDate_sale(String date_sale) {
        this.date_sale = date_sale;
    }

    /**
     * Method 'createPk'
     * 
     * @return SalePk
     */
    public SalePk createPk() { return new SalePk(id); }
}
