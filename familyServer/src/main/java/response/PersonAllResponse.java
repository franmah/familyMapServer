package response;

import models.Person;

/**
 * Response used by PersonAllService class.
 * Contain an array of Person.
 * People can be added using an array and calling the constructor
 * or can be passed one by one using the addPerson method.
 */
public class PersonAllResponse extends Response{
    
    private Person[] people = null;
    
    public PersonAllResponse(){}
    
    public PersonAllResponse(Person[] people){
        this.people = people;
    }
    
    public void addPerson(Person person){
        
    }
}