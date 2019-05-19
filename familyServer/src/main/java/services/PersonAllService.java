package services;

import models.*;
import response.Response;
import response.PersonAllResponse;
import response.ErrorResponse;

/**
 * return every person in the database, related to the user's AuthToken
 * Handle DataBaseException.  
 */
public class PersonAllService{
    
    public PersonAllService(){}
    
    /**
     * Get the user from the AuthToken and return every person related to him/her.
     * 
     * @param   token: AuthToken for the user
     * @return  PersonAllResponse with a person array.
     *          If something goes wrong, return an ErrorResponse.
     */
    public static Response getPersonAll(AuthToken token){
        
        return null;
    }
}