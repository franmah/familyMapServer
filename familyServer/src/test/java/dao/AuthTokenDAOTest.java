package dao;

import org.junit.*;
import static org.junit.Assert.*;
import models.AuthToken;

public class AuthTokenDAOTest {
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
    public void deletePass() throws  Exception{
        AuthTokenDAO atd = db.getAutToken_dao();
        assertTrue(atd.deleteTokens());
    }
    @Test
    public void addPass() throws Exception{
        AuthTokenDAO atd = db.getAutToken_dao();

        AuthToken token = new AuthToken("test");

        boolean success = false;
        try{
            success = atd.addToken(token);
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);

    }

    @Test
    public void addFail(){
        AuthTokenDAO atd = db.getAutToken_dao();

        AuthToken token1 = new AuthToken("test");
        AuthToken token2 = new AuthToken(token1.getToken(), "test2");

        boolean success = true;

        try{
            atd.deleteTokens();
            atd.addToken(token1);
            success = atd.addToken(token2);
        }
        catch (Exception e){
            success = false;
        }
        assertFalse(success);

    }

    @Test
    public void isConnectedPass(){
        AuthTokenDAO atd = db.getAutToken_dao();

        AuthToken token = new AuthToken("test");

        boolean success = false;

        try{
            atd.deleteTokens();
            atd.addToken(token);
            success = atd.isConnected(token);
        }
        catch (Exception e){
            success = false;
        }
        assertTrue(success);
    }

    @Test
    public void isConnectedFail(){
        AuthTokenDAO atd = db.getAutToken_dao();

        AuthToken token = new AuthToken("test");

        boolean success = false;

        try{
            atd.deleteTokens();
            success = atd.isConnected(token);
        }
        catch (Exception e){
            success = false;
        }
        assertFalse(success);
    }

    @Test
    public void  isUserConnected(){
        UserDAO udao = db.getUser_dao();
        AuthTokenDAO atdao = db.getAutToken_dao();

        models.User user = new models.User("test", "password", "test@test.com", "this",
                "test", "f", "test_id");
        boolean success = true;
        try{
            udao.deleteUsers();
            udao.addUser(user);

            String str = udao.connectUser("test", "password");
            System.out.println(str);

            AuthToken token = new AuthToken(str, user.getUserName());

            success = atdao.isConnected(token);
        }
        catch (Exception e){
            success = false;
        }

        assertTrue(success);
    }

}
