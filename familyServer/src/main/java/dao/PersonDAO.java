package dao;

import models.Person;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage Person entries in the database:
 * Add and fetch Persons.
 */
public class PersonDAO{

    private Connection connection = null;
    
    public PersonDAO(){}

    public PersonDAO(Connection connection){ this.connection = connection; }
    
    /**
     * Create the query needed to add a person into the database.
     * Then call the Insert method from OperationDAO.
     * @param   person: the Person object to add
     * @return  true if the person was added, else return false.
     */
    public boolean addPerson(Person person) throws DataBaseException{

        // Check if person already exists
        Person tmp_person = getPerson(person.getPersonId(), person.getUserName());
        if(tmp_person != null){
            System.out.println(LocalTime.now() + "personDAO.addPerson(): Error: person already in database");
            return false;
        }

        // Create update statement:
        String query = "INSERT INTO persons VALUES(?,?,?,?,?,?,?,?);";

        PreparedStatement stmt = null;

        try{

            stmt = connection.prepareStatement(query);

            stmt.setString(1, person.getPersonId());
            stmt.setString(2, person.getUserName());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherId());
            stmt.setString(7, person.getMotherId());
            stmt.setString(8, person.getSpouseId());

            stmt.executeUpdate();

            System.out.println(LocalTime.now() + " personDAO.addPerson(): person: \"" + person.getPersonId() + "\" has been added");
            return true;

        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + "  personDao.addPerson(): ERROR: " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + "personDao.addPerson(): " + e.toString());
            e.getStackTrace();
            throw new DataBaseException("Something went wrong while trying to add the person");
        }
        finally{
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " personDao.addPerson(): ERROR couldn't close prepared statement, " + e.toString());
                    throw new DataBaseException("Internal error: unable to add person");
                }
            }
        }

    }

    /**
     * Get a person according to it's id
     * @param   person_id: the id of the person to fetch.
     * @return  the Person, will be null if the Person wasn't found.
     */
    public Person getPerson(String person_id, String user_name) throws DataBaseException{
        assert person_id != null : "PersondAO.getPerson: person_id is null";
        assert user_name != null : "PersondAO.getPerson: user_name is null";

        Person person = null;

        ResultSet result = null;
        PreparedStatement stmt = null;

        String query = "SELECT * FROM persons WHERE person_id = ?";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, person_id);

            result = stmt.executeQuery();

            if(result.next()){
                System.out.println(LocalTime.now() + " personDAO.getPerson(): person \"" + person_id + "\" has been found.");

                person = new Person(result.getString("person_id"),
                                    result.getString("person_id"),
                                    result.getString("first_name"),
                                    result.getString("last_name"),
                                    result.getString("gender"));

                person.setFatherId(result.getString("father_id"));
                person.setMotherId(result.getString("mother_id"));
                person.setSpouseId(result.getString("spouse_id"));
            }
            if(person != null){
                System.out.println(LocalTime.now() + " personDAO.getPerson() : Fetched person: \"" + person.getPersonId() + "\".");

                if(!person.getUserName().equals(user_name)){
                    System.out.println(LocalTime.now() + " personDAO.getPerson() : usernames don't correspond.");
                    throw new DataBaseException("Wrong user.");
                }
            }
            else{
                System.out.println(LocalTime.now() + " personDAO.getPerson() : Person \"" + person_id + "\" not found.");
            }

            return person;

        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " personDao.getPerson(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " personDAO.getPerson(): ERROR while getting data from person: " + e.toString());
            throw new DataBaseException("Error while getting data from persons table");
        }
        finally {
            if(result != null){
                try{
                    result.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " personDao.getPerson(): ERROR couldn't close result, " + e.toString());
                    throw new DataBaseException("Couldn't get person");
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " personDao.getPerson(): ERROR couldn't close prepared statement, " + e.toString());
                    throw new DataBaseException("Couldn't get person");
                }
            }
        }
    }
    
    /**
     * Add multiple people to the database.
     * Note: for now it will create a query for each person and add them one by one.
     * This requires a lot of overhead work (multiple connections to the databse and multiple Insert) and might have to be avoided.
     * 
     * @return  An array with every Person fetched.
     */
    public List<Person> getPersonAll(String user_name) throws DataBaseException{
        assert user_name != null : "PersondAO.getPersonAll(): user_name is null";

        List<Person> people = new ArrayList<>();
        Person person = null;

        ResultSet result = null;
        PreparedStatement stmt = null;

        String query = "SELECT * FROM persons WHERE user_name = ?;";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, user_name);
            result = stmt.executeQuery();

            // Fill array of Person
            while(result.next()){

                person = new Person(result.getString("person_id"),
                        result.getString("person_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("gender"));

                person.setFatherId(result.getString("father_id"));
                person.setMotherId(result.getString("mother_id"));
                person.setSpouseId(result.getString("spouse_id"));

                people.add(person);
            }

            return people;
        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " personDao.getPersonAll(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " personDAO.getPersonAll(): ERROR while retrieving data from person table. " + e.toString());
            e.printStackTrace();
            throw new DataBaseException("Error while getting data from persons table");
        }
        finally {
            if(result != null){
                try{
                    result.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " personDao.getPersonAll(): ERROR couldn't close result, " + e.toString());
                    throw new DataBaseException("Couldn't retrieve people.");
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " personDao.getPersonAll(): ERROR couldn't close prepared statement, " + e.toString());
                    throw new DataBaseException("Couldn't retrieve people.");
                }
            }
        }
    }

    /** Remove every person entry in the database (does not remove the person table)
     * @return true if data is removed, else: throw a DataBaseException error.
     */
    public boolean deletePersons() throws  DataBaseException{

        PreparedStatement stmt = null;

        try{
            String query = "DELETE FROM persons;";
            stmt = connection.prepareStatement(query);
            stmt.executeUpdate();


            System.out.println(LocalTime.now() + " PersonDao.deletePerson(): data in persons table cleared");
            return true;
        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " PersonDao.deletePersons(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " PersonDAO.deletePersons(): ERROR while deleting data from persons: " + e.toString());
            throw new DataBaseException("Error while deleting data from persons table");
        }
        finally {
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " PersonDao.deletePerson(): ERROR couldn't close prepared statement, " + e.toString());
                    throw new DataBaseException("Couldn't add person");
                }
            }
        }

    }

}