package response;

/**
 * Response used by RegisterService and LoginService.
 * Indicates a user that he/she has been connected (and registered).
 */
public class ConnectionResponse extends Response{
    
    private String token = null;
    private String user_name = null;
    private String person_id = null;
    
    public ConnectionResponse(){}

    public ConnectionResponse(String token, String user_name, String person_id) {
        this.token = token;
        this.user_name = user_name;
        this.person_id = person_id;
    }



}