package dao;


import models.Event;

/**
 * Manage Events in the database.
 * Add and fetch events.
 */
public class EventDAO{
    
    public EventDAO(){}
    
    /**
     * Create a String with the query to add an event.
     * Then call the Insert method in OperationDAO.
     * @param   event: the event to add.
     * @return  true if event is added, else return false.
     */
    public static boolean addEvent(Event event) throws DataBaseException{
     
        return false;   
    }
    
    /**
     * Create a String with the query to fetch a specific event.
     * Then call the Select method in OperationDAO.
     * @param   event_id: the id of the event.
     * @return  the event as an Event object, return null if the event is not found.
     */
    public static Event getEvent(String event_id) throws DataBaseException {
        
        return null;
    }
    
    /**
     * Create a String with the query to fetch all events.
     * Then Call the Select method in OperationDAO.
     * @return  an array containing the events, as Event objects, fetched from the database.
     */
    public static Event[] getEventAll() throws DataBaseException {
        
        return null;
    }
}