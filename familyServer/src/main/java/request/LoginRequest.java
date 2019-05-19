package request;


/**
 * Request used by LoginService class.
 */
public class LoginRequest{
    
    private String user_name = null;
    private String password = null;
    
    public LoginRequest(){}
    
    public LoginRequest(String user_name, String password){
        this.user_name = user_name;
        this.password = password;
    }
    
    public String getUserName(){return user_name;}
    public String getPasswor(){return password;}
    
    public void setUserName(String user_name){
        this.user_name = user_name;
    }
    public void setPassword(String password){
        this.password = password;
    }
}