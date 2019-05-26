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

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }
}