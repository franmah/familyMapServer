package services;

import models.*;
import response.Response;
import response.ErrorResponse;
import response.EventResponse;

/**
 * Get a single event.
 * Handle DataBaseException.
 */
public class EventService{
    
    public EventService(){}
    
    /**
     * Get the requested event if the user is logged in.
     * @param   token: user's token.
     * @param   event_id: event to find.
     * @return  return an EventResponse if the event is fetched.
     *          Else return an ErrorResponse.
     */
    public static Response getEvent(AuthToken token, String event_id){
        
        return null;
    }

    public Response getEventAll(){

        return null;
    }
}
