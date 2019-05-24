package dao;

import org.junit.*;
import java.time.LocalTime;
import static org.junit.Assert.*;

public class ClearDAOTest {

    @Before
    public void setUp(){}

    @After
    public void tearDown(){
    }

    @Test
    public void clearDBPass(){
        ClearDAO clear_dao = new ClearDAO();
        boolean success = false;

        try{
            success = clear_dao.clearDataBase();
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " ClearDAOTest: Exception caught: test fails");
            success = false;
        }

        assertTrue(success);

    }
}
