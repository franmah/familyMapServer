package services;

import java.time.LocalTime;
import dao.DataBaseException;
import dao.OperationDAO;
import dao.UserDAO;
import response.ConnectionResponse;
import response.ErrorResponse;
import response.Response;
import request.LoginRequest;
import models.User;

/**
 * Login a user if password is right and user is registered.
 * Handle DataBaseException.
 */
public class LoginService{

    public LoginService(){}
    
    /**
     * Connect a user using a user_name and a password
     * 
     * @param   req: The request containing the user_name and password
     * @return  error message if user wasn't connected
     *          else: token, user_name and person id
     */
    public static Response LoginUser(LoginRequest req){

        String user_name = req.getUserName();
        String password = req.getPassword();
        boolean commit = false;

        OperationDAO db = null;

        try{
            db = new OperationDAO();
            UserDAO udao =  db.getUser_dao();

            System.out.println(LocalTime.now() + " LoginService: connecting user...");
            String token = udao.connectUser(user_name, password);

            if(token == null){
                commit = false;
                System.out.println(LocalTime.now() + " LoginService: token came back null, password/username error");
                return  new ErrorResponse("Wrong Username and/or Password.");
            }
            else{
                commit = true;
                User user = udao.getUser(user_name);
                System.out.println(LocalTime.now() + " LoginService: user has been connected, committing....");
                return new ConnectionResponse(token, user_name, user.getPersonId());
            }

        }
        catch (DataBaseException message){
            commit = false;
            return new ErrorResponse(message.toString());
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " LoginService: Error: " + e.toString());
            commit = false;
            return new ErrorResponse("Internal error: something went wrong while trying to connect user.");
        }
        finally{
            if(db != null){
                db.commitAndCloseConnection(commit);
                if(commit) {
                    System.out.println(LocalTime.now() + " LoginService: connection closed -> changes were commited");
                }
                else{
                    System.out.println(LocalTime.now() + " LoginService: connection closed -> changes not were commited");
                }
            }
        }
    }
    
}