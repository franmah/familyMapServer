package services;
import java.time.LocalTime;

import dao.DataBaseException;
import dao.OperationDAO;
import dao.PersonDAO;
import dao.UserDAO;
import request.LoginRequest;
import response.ErrorResponse;
import response.Response;
import response.ConnectionResponse;
import request.RegisterRequest;
import models.*;

/**
 * Create a new user and log him/her in
 * Handle DataBaseException.
 */
public class RegisterService{
    
    
    public RegisterService(){}
    
    /**
     * Create a Person and a new User (link the person_id to the user)
     * Log in the new user
     * 
     * @param req   RegisterRequest containing the user info
     * @return      Return a connexionResponse with the user id, name and token
     *              or errorResponse if there is a missing field or if the username is already taken
     */
    public static Response registerNewUser(RegisterRequest req){

        // Check input
        if(req.getUser_name() == null || req.getPassword() == null || req.getFirst_name() == null ||
                req.getLast_name() == null || req.getEmail() == null || req.getGender() == null){
            // return error
        }

        String gender_choice = "fm";
        String gender = req.getGender().toLowerCase();
        if(gender.length() != 1 && !gender_choice.contains(gender)){
            // Return error response (wrong gender)
        }

        // Create user and person object
        Person new_person = new Person(req.getUser_name(), req.getFirst_name(), req.getLast_name(), req.getGender());

        User new_user = new User(req.getUser_name(), req.getPassword(), req.getEmail(), req.getFirst_name(),
                                    req.getLast_name(), req.getGender(), new_person.getPersonId());

        OperationDAO db = null;
        boolean commit = false;

        try {
            db = new OperationDAO();
            UserDAO udao = db.getUser_dao();
            PersonDAO pdao = db.getPerson_dao();

            // Add user
            if (!udao.addUser(new_user)) {
                System.out.println(LocalTime.now() + " RegisterService: username is already used");
                return new ErrorResponse("The username is already used!");
            }

            // Add person
            if (!pdao.addPerson(new_person)){
                System.out.println(LocalTime.now() + " RegisterService: Error while trying to add Person object," +
                                                        "person might already be in database while it shouldn't");
                throw new DataBaseException("Internal Error: something went wrong while registering user.");
            }

            // Login User : no need to check for input since the user was added above.
            System.out.println(LocalTime.now() + " RegisterService: connecting user...");
            String token = udao.connectUser(new_user.getUserName(), new_user.getPassword());

            if(token == null){
                System.out.println(LocalTime.now() + " RegisterService: Error: token came back null");
                throw new DataBaseException("Internal Error: something went wrong while connecting the user, user not registered");
            }

            // Generate family tree for user.

        }
        catch (DataBaseException message){
            return new ErrorResponse(message.toString());
        }
        catch (Exception e){
            return new ErrorResponse("Internal error: unable to register user.");
        }
        finally {
            try {
                db.commitAndCloseConnection(commit);
                if(commit){
                    System.out.println(LocalTime.now() + " RegisterService: connection closed -> changes were committed");
                }
                else {
                    System.out.println(LocalTime.now() + " RegisterService: connection closed -> changes were not committed");
                }
            }
            catch (Exception e){
                System.out.println(LocalTime.now() + " RegisterService: error while trying to close db connection: " + e.toString());
            }
        }


        return null;
    }
    
}