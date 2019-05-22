package dao;

import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;
import models.Person;


public class PersonDAOTest {
    private OperationDAO db= null;
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
    public void deletePass() throws Exception{
        PersonDAO pdao = db.getPerson_dao();
        boolean success = false;

        try {
            success = pdao.deletePersons();
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void insertPass() throws Exception {
        PersonDAO pdao = db.getPerson_dao();
        Person person = new Person("test", "test", "this", "test", "m");

        // insert without optional members (father_id...)
        boolean success = false;

        try {
            success = pdao.addPerson(person);
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);

        // insert with optional members (father_id...)
        Person person2 = new Person("test2", "test", "this", "test", "m");
        person2.setFatherId("MR test");
        person2.setMotherId("Mrs test");
        person2.setSpouseId("Mrs testee");

        success = false;

        try {
            success = pdao.addPerson(person2);
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void insertFail() throws Exception {
        PersonDAO pdao = db.getPerson_dao();
        Person person = new Person("test", "test", "this", "test", "m");

        // insert without optional members (father_id...)
        boolean success = true;

        try {
            pdao.addPerson(person);

            // Insert person again: should fail!
            success = pdao.addPerson(person);
        }
        catch (Exception e){
            success = false;
        }

        assertFalse(success);


    }

    @Test
    public void getPersonPass() throws  Exception{
        PersonDAO pdao = db.getPerson_dao();

        Person person = new Person("test", "test", "this", "test", "m");
        Person compare_person = null;

        try{
            pdao.addPerson(person);
            compare_person = pdao.getPerson("test");
        }
        catch (Exception e){
            assertTrue(false);
        }

        assertEquals(person, compare_person);
    }

    @Test
    public void getPersonFail() throws Exception{
        PersonDAO pdao = db.getPerson_dao();

        Person person = new Person("test", "test", "this", "test", "m");
        Person compare_person = null;

        try{
            pdao.addPerson(person);
            // The names are different.
            compare_person = pdao.getPerson("different_test");
        }
        catch (Exception e){
            assertFalse(false); // This test should fail because getPerson will return an exception.
        }

        assertNotEquals(person, compare_person);
    }

    /**
     * Add some dummy entries to the database and check if getPersonAll return the right number of entry.
     */
    @Test
    public void getPersonAllPass() throws Exception{
        PersonDAO pdao = db.getPerson_dao();

        final int NUM_ROWS = 10;

        for(int i = 0; i < NUM_ROWS; i++) {
            pdao.addPerson(new Person(String.format("test" + i), "test", "this", "test", "m"));
        }

        boolean success = false;

        try{
            List<Person> people = pdao.getPersonAll();
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
