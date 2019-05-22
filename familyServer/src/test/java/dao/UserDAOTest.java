package dao;

import org.junit.*;
import static org.junit.Assert.*;

import models.User;


public class UserDAOTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        UserDAO udao = new UserDAO();
        udao.deleteUsers();
    }

    @Test
    public void insertPass() throws Exception {
        UserDAO udao = new UserDAO();
        udao.deleteUsers();

        User usr = new User("test", "password", "test@test.com", "this",
                "test", "f", "test_id");

        assertTrue(udao.addUser(usr));
    }

    @Test
    public void insertFail() throws Exception {
        UserDAO udao = new UserDAO();
        udao.deleteUsers();


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
    public void deletePass() throws  Exception{
        UserDAO udao = new UserDAO();

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
        UserDAO udao = new UserDAO();

        udao.deleteUsers();

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
        UserDAO udao = new UserDAO();

        udao.deleteUsers();

        User usr_compare = udao.getUser("test");

        assertEquals(usr_compare, null);
    }

    @Test
    public void connectUser(){
        UserDAO udao = new UserDAO();

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
}
