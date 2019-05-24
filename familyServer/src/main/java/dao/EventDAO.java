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

        // Check if person already exists
        Event tmp_event = getEvent(event.getEventId(), event.getUserName());
        if(tmp_event != null){
            System.out.println(LocalTime.now() + " eventDAO.addEvent(): Error: person already in database");
            return false;
        }

        // Create update statement:
        String query = "INSERT INTO events VALUES(?,?,?,?,?,?,?,?,?);";

        PreparedStatement stmt = null;

        try{

            stmt = connection.prepareStatement(query);

            stmt.setString(1, event.getEventId());
            stmt.setString(2, event.getUserName());
            stmt.setString(3, event.getPersonId());
            stmt.setFloat(4,  event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();

            System.out.println(LocalTime.now() + " eventDAO.addEvent(): event: \"" + event.getPersonId() + "\" has been added");
            return true;

        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + "  eventDAO.addEvent(): ERROR: " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " eventDAO.addEvent(): " + e.toString());
            e.getStackTrace();
            throw new DataBaseException("Something went wrong while trying to add the event");
        }
        finally{
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " eventDAO.addEvent(): ERROR unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("Internal error: unable to add event");
                }
            }
        }
    }
    
    /**
     * Create a String with the query to fetch a specific event.
     * Then call the Select method in OperationDAO.
     * @param   event_id: the id of the event.
     * @return  the event as an Event object, return null if the event is not found.
     */
    public Event getEvent(String event_id, String user_name) throws DataBaseException {
        Event event = null;

        ResultSet result = null;
        PreparedStatement stmt = null;

        String query = "SELECT * FROM events WHERE event_id = ?";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, event_id);

            result = stmt.executeQuery();

            if(result.next()){
                System.out.println(LocalTime.now() + " EventDAO.getEvent(): event has been found");

                event = new Event(result.getString("event_id"),
                        result.getString("user_name"),
                        result.getString("person_id"),
                        result.getFloat("latitude"),
                        result.getFloat("longitude"),
                        result.getString("country"),
                        result.getString("city"),
                        result.getString("event_type"),
                        result.getInt("year"));
            }

            if(event != null){
                System.out.println(LocalTime.now() + " EventDAO.getEvent() : Fetched event: \"" + event.getEventId() + "\"");

                if(event.getUserName().equals(user_name)){
                    System.out.println(LocalTime.now() + " EventDAO.getEvent() : usernames correspond");
                    return event;
                }
                else {
                    System.out.println(LocalTime.now() + " EventDAO.getEvent() : usernames don't correspond");
                    throw new DataBaseException("The event you requested belongs to another user");
                }
            }
            else{
                System.out.println(LocalTime.now() + " EventDAO.getEvent() : No event \"" + event_id + "\" found");
                return null;
            }

        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " EventDAO.getEvent(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " EventDAO.getEvent(): ERROR while retrieving data from events: " + e.toString());
            throw new DataBaseException("Internal Error: unable to access event.");
        }
        finally {
            if(result != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " EventDAO.getEvent(): ERROR unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("unable to get event");
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " EventDAO.getEvent(): ERROR unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("unable to get event");
                }
            }
        }
    }
    
    /**
     * Create a String with the query to fetch all events.
     * Then Call the Select method in OperationDAO.
     * @return  an array containing the events, as Event objects, fetched from the database.
     */
    public List<Event> getEventAll(String user_name) throws DataBaseException {

        List<Event> events = new ArrayList<>();
        Event event = null;

        ResultSet result = null;
        PreparedStatement stmt = null;

        String query = "SELECT * FROM events WHERE user_name = ?;";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, user_name);
            result = stmt.executeQuery();

            // Fill array of Event
            while(result.next()){

                event = new Event(result.getString("event_id"),
                        result.getString("user_name"),
                        result.getString("person_id"),
                        result.getFloat("latitude"),
                        result.getFloat("longitude"),
                        result.getString("country"),
                        result.getString("city"),
                        result.getString("event_type"),
                        result.getInt("year"));

                events.add(event);
            }

            return events;
        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " EventDao.getEventAll(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " EventDao.getEventAll(): ERROR while retrieving data from events table. " + e.toString());
            e.printStackTrace();
            throw new DataBaseException("Error while getting data from events table");
        }
        finally {
            if(result != null){
                try{
                    result.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " EventDao.getEventAll(): ERROR unable to close result, " + e.toString());
                    throw new DataBaseException("Unable to retrieve events.");
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " EventDao.getEventAll(): ERROR unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("Unable to retrieve events.");
                }
            }
        }
    }

    public boolean deleteEvents() throws  DataBaseException{
        PreparedStatement stmt = null;

        try{
            String query = "DELETE FROM events;";
            stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " EventDAO.deleteEvents(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " EventDAO.deleteEvents(): ERROR while deleting data from events: " + e.toString());
            throw new DataBaseException("Error while deleting data from events table");
        }
        finally {
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " EventDAO.deleteEvents(): ERROR Unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("Unable to delete events");
                }
            }
        }

        System.out.println(LocalTime.now() + " EventDAO.deleteEvents(): data in events table cleared");
        return true;
    }
}