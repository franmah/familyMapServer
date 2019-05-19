package models;

/**
 * Client user. 
 * Users use their user_name and password to connect to the server.
 */
public class User{
    
    public String user_name = null;
    public String password = null;
    public String email = null;
    public String first_name = null;
    public String last_name = null;
    public String gender = null;
    public String person_id = null;
    
    public User(){}
    
    public User(String user_name, String password, String email, String first_name, String last_name,
                    String gender, String person_id){
    
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.person_id = person_id;
    }
    
    
    
}