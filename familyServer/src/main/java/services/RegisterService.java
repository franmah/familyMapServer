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

    OperationDAO db = null;
    
    public RegisterService(){}
    
    /**
     * Create a Person and a new User (link the person_id to the user)
     * Log in the new user
     *
     * @param req   RegisterRequest containing the user info
     * @return      Return a connexionResponse with the user id, name and token
     *              or errorResponse if there is a missing field or if the username is already taken
     */
    public Response registerNewUser(RegisterRequest req){

        // Check input
        if(req.getUser_name() == null || req.getPassword() == null || req.getFirst_name() == null ||
                req.getLast_name() == null || req.getEmail() == null || req.getGender() == null){
            System.out.println(LocalTime.now() + " RegisterService.registerNewUser(): Error: One of the parameter is null");
            return new ErrorResponse("Error: one of the parameter is missing");
        }

        String gender_choice = "fm";
        String gender = req.getGender().toLowerCase();
        if(gender.length() != 1 || !gender_choice.contains(gender)){
            System.out.println(LocalTime.now() + " RegisterService.registerNewUser(): Error: gender input wrong.");
            return new ErrorResponse("Error: enter \"f\" or \"m\" for gender");
        }

        boolean commit = false;

        try {
            db = new OperationDAO();
            UserDAO udao = db.getUser_dao();
            PersonDAO pdao = db.getPerson_dao();

            // Check if user is registered
            if(udao.getUser(req.getUser_name()) != null){
                System.out.println(LocalTime.now() + " RegisterService: username is already used");
                commit = false;
                return new ErrorResponse("The username is already used!");
            }

            User new_user = generatePersonAndUser(req);

            // Login User : no need to check for input since the user was added above.
            System.out.println(LocalTime.now() + " RegisterService: connecting user...");
            String token = udao.connectUser(new_user.getUserName(), new_user.getPassword());

            if(token == null){
                System.out.println(LocalTime.now() + " RegisterService: Error: token came back null");
                commit = false;
                return  new ErrorResponse("Internal Error: something went wrong while connecting the user, user not registered");
            }

            commit = true;
            db.commitAndCloseConnection(commit);
            db = null;

            System.out.println(LocalTime.now() + " RegisterService: call to FillService to generate tree.");
            final int NUM_GENERATION = 4;
            FillService fill_service = new FillService();
            fill_service.fillUserTree(req.getUser_name(), NUM_GENERATION);

            return new ConnectionResponse(token, new_user.getUserName(), new_user.getPersonId());

        }
        catch (DataBaseException message){
            commit = false;
            return new ErrorResponse(message.toString());
        }
        catch (Exception e){
            commit = false;
            return new ErrorResponse("Internal error: unable to register user.");
        }
        finally {
            if(db != null){
                db.commitAndCloseConnection(commit);
            }

        }
    }

    private User generatePersonAndUser(RegisterRequest req) throws DataBaseException{
        try {
            // Create user and person object
            Person new_person = new Person(req.getUser_name(), req.getFirst_name(), req.getLast_name(), req.getGender());

            User new_user = new User(req.getUser_name(), req.getPassword(), req.getEmail(), req.getFirst_name(),
                    req.getLast_name(), req.getGender(), new_person.getPersonId());


            // Add user
            if (!db.getUser_dao().addUser(new_user)) {
                System.out.println(LocalTime.now() + " RegisterService: username is already used");
                throw new DataBaseException("The username is already used!");
            }

            // Add person
            if (!db.getPerson_dao().addPerson(new_person)) {
                System.out.println(LocalTime.now() + " RegisterService: Error while trying to add Person object," +
                        "person might already be in database when it shouldn't");
                throw new DataBaseException("Internal Error: something went wrong while registering user.");
            }

            return new_user;
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " RegisterService.generatePersonAndUser(): Error: " + e.toString());
            e.printStackTrace();
            throw  new DataBaseException("Internal error: unable to register user.");
        }
    }
    
}