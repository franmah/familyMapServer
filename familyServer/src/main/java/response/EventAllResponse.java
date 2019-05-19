package response;

import models.Event;

/**
 * Response used by EventAllService class.
 * Contain an array of Event.
 * Events can be added using an array and calling the constructor
 * or can be passed one by one using the add Event method.
 */
public class EventAllResponse extends Response{
    
    private Event[] events = null;
    
    public EventAllResponse(){}
    
    public EventAllResponse(Event[] events){
        this.events = events;
    }
    
    public void addEvent(Event event){
        
    }
    
    
}