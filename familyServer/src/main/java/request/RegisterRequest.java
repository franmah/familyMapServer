package request;

/**
 * Request used by RegisterService class.
 */
public class RegisterRequest{
    
    private String user_name = null;
    private String password = null;
    private String email = null;
    private String first_name = null;
    private String last_name = null;
    private String gender = null;

    public RegisterRequest(){}
    
    public RegisterRequest(String user_name, String password, String email, String first_name,
        String last_name, String gender){
            
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
            
    }
    
    

}