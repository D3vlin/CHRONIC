package co.edu.iumafis.chronic.model.dto.user;

import java.io.Serializable;

/**
 * This class represents the user model.
 * 
 * @author Alexis Duque
 * @version 1.0
 * @since 2020-03-28
 */
public final class UserDto implements Serializable {
    
    /** 
     * This attribute maps to the column id_user in the users table.
     */
    protected int id_user;

    /** 
     * This attribute maps to the column nickname in the users table.
     */
    protected String nickname;

    /** 
     * This attribute maps to the column password in the users table.
     */
    protected String password;

    /**
     * Empty Constructor.     * 
     */
    public UserDto() {}
    
    /**
     * Constructor.
     * 
     * @param id_user
     * @param nickname
     * @param password
     */    
    public UserDto(int id_user, String nickname, String password) {
        
        this.id_user = id_user;
        this.nickname = nickname;
        this.password = password;
    }

    /**
     * Gets the value of id_user.
     * 
     * @return int
     */
    public int getIdUser() { return id_user; }

    /**
     * Sets the value of id_user.
     * 
     * @param id_user
     */
    public void setIdUser(int id_user) { this.id_user = id_user; }

    /**
     * Gets the value of nickname.
     * 
     * @return String
     */
    public String getNickName() { return nickname; }

    /**
     * Sets the value of nickname.
     * 
     * @param nickname
     */
    public void setNickName(String nickname) { this.nickname = nickname; }

    /**
     * Gets the value of password.
     * 
     * @return String
     */
    public String getPassword() { return password; }

    /**
     * Sets the value of password.
     * 
     * @param password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Method 'createPk'
     * 
     * @return UserPk
     */
    public UserPk createPk() { return new UserPk(id_user); }
}