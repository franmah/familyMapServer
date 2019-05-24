package services;


import models.*;
import response.Response;
import response.PersonResponse;
import response.ErrorResponse;

/**
 * Get one person using his/her id.
 * The person must be related to the user doing the request.
 * Handle DataBaseException.
 */
public class PersonService{
    
    public PersonService(){}
    
    /**
     * if user is logged in, get a person if that person is related to the user.
     * 
     * @param   token: user's AuthToken.
     * @param   person_id: person to return.
     * @return  PersonResponse with person's info,
     *          return ErrorResponse if an error occurs.
     */
    public static Response getPerson(AuthToken token, String person_id){
        // NEED TO CHECK TOKEN!
        // SHOULD YOU HAVE A AUTHTOKEN OR SIMPLY A STRING TOKEN ??
        return null;
    }
}