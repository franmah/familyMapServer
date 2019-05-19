package services;

import response.ConnexionResponse;
import response.Response;
import request.LoginRequest;
import models.*;

/**
 * Login a user if password is right and user is registered.
 * Handle DataBaseException.
 */
public class LoginService{
    
    public LoginService(){}
    
    /**
     * Connect a user using a user_name and a password
     * 
     * @param   The request containing the user_name and password
     * @return  error message if user wasn't connected
     *          else: token, user_name and person id
     */
    public static Response LoginUser(LoginRequest req){
        
        return null;
    }
    
}