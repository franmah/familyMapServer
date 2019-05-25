package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.*;

import dao.OperationDAO;

import static org.junit.Assert.assertTrue;

public class FillServiceTest {

/*
    @Test
    public void birthTest(){
        try {
            OperationDAO db = new OperationDAO();
            db.getEvent_dao().deleteEvents();
            db.getPerson_dao().deletePersons();
            db.getUser_dao().deleteUsers();

            User user = new User("test_user", "password", "email", "first", "last", "f", "test_person");
            Person person = new Person("test_person", "test_user", "first", "last", "f");
            Event event = new Event("test_event", "test_user", "test_person", (float) 10, (float) 10, "Counttry", "city", "birth", 1993);

            db.getUser_dao().addUser(user);
            db.getPerson_dao().addPerson(person);
            db.getEvent_dao().addEvent(event);
            db.commitAndCloseConnection(true);


            // birth event.
            FillService service = new FillService();
            if(service.generateRandomEvent(person.getPersonId(), user.getUserName())){
                System.out.println("success");
            }
            else{
                System.out.println("failure");
            }


            db.commitAndCloseConnection(false);
        }
        catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    */
    @Test
    public void fillTreeCompleteTest(){
        try {
            OperationDAO db = new OperationDAO();
            db.getEvent_dao().deleteEvents();
            db.getPerson_dao().deletePersons();
            db.getUser_dao().deleteUsers();

            User user = new User("test_user", "password", "email", "first", "last", "f", "test_person");
            Person person = new Person("test_person", "test_user", "first", "last", "f");
            Event event = new Event("test_event", "test_user", "test_person", (float) 10, (float) 10, "Counttry", "city", "birth", 1993);

            db.getUser_dao().addUser(user);
            db.getPerson_dao().addPerson(person);
            db.getEvent_dao().addEvent(event);
            db.commitAndCloseConnection(true);

            FillService service = new FillService();
            service.fillUserTree("test_user", 4);

        }
        catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

}
