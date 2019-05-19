package request;

import models.*;

/**
 * Contains arrays of User, Person and Event.
 * Arrays can be passed using constructor or can be added one by one using add methods.
 */
public class LoadRequest{
    
    private User[] users = null;
    private Person[] people = null;
    private Event[] events = null;
    
    public LoadRequest(){}
    
    public LoadRequest(User[] users, Person[] people, Event[] events){
        this.users = users;
        this.people = people;
        this.events = events;
    }      
    
    public void addUser(User user){ 
        
    }
    
    public void addPerson(Person person){
        
    }
    
    public void addEvent(Event event){
        
    }
    
}