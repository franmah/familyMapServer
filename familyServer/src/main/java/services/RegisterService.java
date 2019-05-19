package services;
import response.Response;
import response.ConnexionResponse;
import request.RegisterRequest;
import models.*;

/**
 * Create a new user and log him/her in
 * Handle DataBaseException.
 */
public class RegisterService{
    
    
    public RegisterService(){}
    
    /**
     * Create a Person and a new User (link the person_id to the user)
     * Log in the new user
     * 
     * @param req   RegisterRequest containing the user info
     * @return      Return a connexionResponse with the user id, name and token
     *              or errorResponse if there is a missing field or if the username is already taken
     */
    public static Response registerNewUser(RegisterRequest req){
        // Create a person
        
        // create a user using the person_id from the person that was generated
        
        // Log that person using service.LoginService
        
        
        return null;
    }
    
}