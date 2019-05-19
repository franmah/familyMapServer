package response;

/**
 * Response used by RegisterService and LoginService.
 * Indicates a user that he/she has been connected (and registered).
 */
public class ConnexionResponse extends Response{
    
    private String token = null;
    private String user_name = null;
    private String person_id = null;
    
    public ConnexionResponse(){}
}