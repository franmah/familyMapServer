package dao;

import org.junit.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.sql.Connection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.*;

import models.*;


public class UserDAOTest {
    private OperationDAO db = null;

    @Before
    public void setUp() throws Exception {
        db = new OperationDAO();
    }

    @After
    public void tearDown() throws Exception {
        db.commitAndCloseConnection(false);
        db = null;
    }


    @Test
    public void insertPass() throws Exception {
        UserDAO udao = db.getUser_dao();

        User usr = new User("test", "password", "test@test.com", "this",
                "test", "f", "test_id");

        assertTrue(udao.addUser(usr));
    }

    @Test
    public void insertFail() throws Exception {
        UserDAO udao = db.getUser_dao();

        User usr = new User("test", "password", "test@test.com", "this",
                "test", "f", "test_id");

        // User should already be in the database!
        boolean success = false;
        try{
            udao.addUser(usr);
            success = udao.addUser(usr);
        }
        catch (Exception e){
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void deleteUsersPass() throws  Exception{
        UserDAO udao = db.getUser_dao();

        boolean success = false;
        try {
            success = udao.deleteUsers();
        }
        catch(Exception e){
            success = false;
        }

        assertTrue(success);

    }

    @Test
    public void getUserPass() throws  Exception{
        UserDAO udao = db.getUser_dao();

        User usr = new User("test", "password", "test@test.com", "this",
                "test", "f", "test_id");

        udao.addUser(usr);

        User usr_compare = udao.getUser("test");

        //System.out.println("usr_compare: " + usr_compare.toString());
        //System.out.println("usr: " + usr.toString());

        assertEquals(usr, usr_compare);
    }

    @Test
    public void getUserFail() throws  Exception{
        UserDAO udao = db.getUser_dao();

        udao.deleteUsers();

        User usr_compare = udao.getUser("test");

        assertEquals(usr_compare, null);
    }

    @Test
    public void connectUserPass(){
        UserDAO udao = db.getUser_dao();

        User user = new User("test", "password", "test@test.com", "this",
                "test", "f", "test_id");
        boolean success = true;
        try{
            udao.deleteUsers();
            udao.addUser(user);
            String str = udao.connectUser("test", "password");
            System.out.println(str);
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void connectUserFail(){
        UserDAO udao = db.getUser_dao();

        boolean success = false;
        try{
            udao.deleteUsers();
            String str = udao.connectUser("test", "password");
            if(str == null) { success = true; }
        }
        catch (Exception e){
            success = true;
        }

        assertTrue(success);
    }

    @Test
    public void getUserAllPass() throws Exception{
        UserDAO user_dao = db.getUser_dao();

        final int NUM_ROWS = 10;

        for(int i = 0; i < NUM_ROWS; i++) {
            user_dao.addUser(new User("test" + i, "password", "test@test.com", "this",
                    "test", "f", "test_id"));
        }

        boolean success = false;

        try{
            ArrayList<User> people = user_dao.getUserAll();
            if(people.size() == NUM_ROWS){
                success = true;
            }
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);
    }

}
