package response;

import models.Person;

/**
 * Response used by PersonService class.
 * Contains the information of the requested person.
 */
public class PersonResponse extends Response{
    
    private String user_name = null;
    private String person_id = null;
    private String first_name = null;
    private String last_name = null;
    private String gender = null;
    private String father_id = null;
    private String mother_id = null;
    private String spouse_id = null;
    
    public PersonResponse(){}


    
}