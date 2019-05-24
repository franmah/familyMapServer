package dao;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import models.Event;


public class EventDAOTest {
    private OperationDAO db = null;

    @Before
    public void setUp(){
        db = new OperationDAO();
    }

    @After
    public void tearDown(){
        db.commitAndCloseConnection(false);
        db = null;
    }

    @Test
    public void deletePass(){
        EventDAO edao = db.getEvent_dao();
        boolean success = false;

        try{
            success = edao.deleteEvents();
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void insertPass(){
        EventDAO edao = db.getEvent_dao();
        Event event = new Event("test", "test", "test", 12.5f,
                                12.5f, "USA", "provo", "birth", 2019);

        boolean success = false;

        try{
            success = edao.addEvent(event);
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void insertFail(){

        EventDAO edao = db.getEvent_dao();
        Event event = new Event("test", "test", "test", 12.5f,
                12.5f, "USA", "provo", "birth", 2019);

        boolean success = true;

        try{
            edao.addEvent(event);
            success = edao.addEvent(event); // Event should already be in table.
        }
        catch (Exception e){
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void getEventPass(){
        EventDAO edao = db.getEvent_dao();
        Event event = new Event("test", "test", "test", 12.5f,
                12.5f, "USA", "provo", "birth", 2019);

        Event tmp_event = null;

        try{
            edao.addEvent(event);
            tmp_event = edao.getEvent(event.getEventId(), event.getUserName());
        }
        catch (Exception e){
            assertTrue(false);
        }

        assertEquals(tmp_event, event);
    }


    @Test
    public void getEventNotFound(){
        EventDAO edao = db.getEvent_dao();
        boolean success = true;

        try{
            // Table events should be empty.
            Event event = edao.getEvent("test", "test");
            if(event == null){
                success = false;
            }
        }
        catch (Exception e){
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void getEventWrongUserName(){

    }
    @Test
    public void getEventAllPass(){
        EventDAO edao = db.getEvent_dao();

        final int NUM_ROWS = 10;

        for(int i = 0; i < NUM_ROWS; i++){
            edao.addEvent(new Event("test" + i, "test_name", "test_person", 12.5f,
                    12.5f, "USA", "provo", "birth", 2019));
        }

        boolean success = false;

        try{
            List<Event> events = edao.getEventAll("test_name");
            System.out.println(events.size());
            if(events.size() == NUM_ROWS){
                success = true;
            }
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);

    }
}
