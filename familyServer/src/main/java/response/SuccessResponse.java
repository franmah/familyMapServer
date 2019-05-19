package response;

/**
 * Response used by Services that only need a to display a message in case of success.
 */
public class SuccessResponse extends Response{
    
    private String message = null;
    
    public SuccessResponse(){}
    
    public SuccessResponse(String message){
        this.message = message;
    }
    
}