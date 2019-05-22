package dao;

import models.Event;
import java.sql.*;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Manage Events in the database.
 * Add and fetch events.
 */
public class EventDAO{
    Connection connection = null;

    public EventDAO(){}

    public EventDAO(Connection connection){ this.connection = connection; }

    public Connection getConnection() { return connection; }

    /**
     * Create a String with the query to add an event.
     * Then call the Insert method in OperationDAO.
     * @param   event: the event to add.
     * @return  true if event is added, else return false.
     */
    public boolean addEvent(Event event) throws DataBaseException{
     
        return false;   
    }
    
    /**
     * Create a String with the query to fetch a specific event.
     * Then call the Select method in OperationDAO.
     * @param   event_id: the id of the event.
     * @return  the event as an Event object, return null if the event is not found.
     */
    public Event getEvent(String event_id) throws DataBaseException {
        
        return null;
    }
    
    /**
     * Create a String with the query to fetch all events.
     * Then Call the Select method in OperationDAO.
     * @return  an array containing the events, as Event objects, fetched from the database.
     */
    public List<Event> getEventAll() throws DataBaseException {

        List<Event> events = new ArrayList<>();
        Event event = null;

        ResultSet result = null;
        PreparedStatement stmt = null;

        String query = "SELECT * FROM users;";

        try{
            stmt = connection.prepareStatement(query);
            result = stmt.executeQuery();

            // Fill array of Event
            while(result.next()){

                event = new Event(result.getString("event_id"),
                        result.getString("user_name"),
                        result.getString("person_id"),
                        result.getFloat("latitude"),
                        result.getFloat("longitude"),
                        result.getString("country"),
                        result.getString("citry"),
                        result.getString("type"),
                        result.getInt("year"));

                events.add(event);
            }

            return events;
        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " eventDao.getEventAll(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " eventDao.getEventAll(): ERROR while retrieving data from events table. " + e.toString());
            e.printStackTrace();
            throw new DataBaseException("Error while getting data from events table");
        }
        finally {
            if(result != null){
                try{
                    result.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " eventDao.getEventAll(): ERROR couldn't close result, " + e.toString());
                    throw new DataBaseException("Unable to retrieve events.");
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " eventDao.getEventAll(): ERROR couldn't close prepared statement, " + e.toString());
                    throw new DataBaseException("Unable to retrieve events.");
                }
            }
        }
    }
}