package services;

import response.Response;
import response.EventAllResponse;
import response.ErrorResponse;
import models.*;

/**
 * Get every event for the current user.
 * Handle DataBaseException.
 */
public class EventAllService{
    
    public EventAllService(){}
    
    /**
     * if the user is logged in, return every events related to that user.
     * @param   token: user's token.
     * @return  return an EventAllResponse with an array of event.
     */
    public static Response getEventAll(AuthToken token){
        
        return null;
    }
}