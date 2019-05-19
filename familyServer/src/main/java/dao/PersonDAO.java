package dao;

import models.Person;

/**
 * Manage Person entries in the database:
 * Add and fetch Persons.
 */
public class PersonDAO{
    
    public PersonDAO(){}
    
    /**
     * Create the query needed to add a person into the database.
     * Then call the Insert method from OperationDAO.
     * @param   person: the Person object to add
     * @return  true if the person was added, else return false.
     */
    public static boolean addPerson(Person person) throws DataBaseException{
        
        return false;
    }
    
    /**
     * Get a person according to it's id
     * @param   person_id: the id of the person to fetch.
     * @return  the Person, will be null if the Person wasn't found.
     */
    public static Person getPerson(String person_id) throws DataBaseException{
        
        return null;
    }
    
    /**
     * Add multiple people to the database.
     * Note: for now it will create a query for each person and add them one by one.
     * This requires a lot of overhead work (multiple connections to the databse and multiple Insert) and might have to be avoided.
     * 
     * @return  An array with every Person fetched.
     */
    public static Person[] getPersonAll() throws DataBaseException{
        
        return null;
    }
}