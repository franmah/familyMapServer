package response;

import models.Event;

/**
 * Response used by EventService class.
 * Return the info for a single event.
 */
public class EventResponse extends Response{
    
    private String id = null;
    private String user_name = null;
    private String person_id = null;
    private float latitude = 0;
    private float longitude = 0;
    private String country = null;
    private String city = null;
    private String type = null;
    private int year = 0;

    public EventResponse(){}

}