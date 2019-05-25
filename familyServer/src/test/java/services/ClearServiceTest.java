package services;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalTime;
import response.ErrorResponse;
import response.Response;
import response.SuccessResponse;



public class ClearServiceTest {

    @Test
    public void clearPass(){
        ClearService clear_service = new ClearService();

        boolean success = false;

        Response response = clear_service.clearDataBase();

        if(response instanceof ErrorResponse){
            System.out.println(LocalTime.now() + " ClearService.clearPass(): response came back as an ErrorResponse");
            success = false;
        }
        if(response instanceof SuccessResponse){
            System.out.println(LocalTime.now() + " ClearService.clearPass(): response came back as a SuccessResponse");
            success = true;
        }

        assertTrue(success);
    }
}
