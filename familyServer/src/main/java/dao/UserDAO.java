package dao;

import models.User;
import models.AuthToken;
import java.time.LocalTime;
import java.sql.*;

/**
 * Perform database operation for users
 */ 
public class UserDAO{

   private OperationDAO db = null;

   public UserDAO(){
      db = new OperationDAO();
   }

   /**
    * Add a user to the database. Create a String with the query and pass it to the select method in OperationDAO
    * 
    * @param    user to add
    * @return   False if the username is already used.
    */
   public boolean addUser(User user) throws DataBaseException {

      // Check if user is registered.
      User tmp_user = getUser(user.getUserName());
      if(tmp_user != null){
         System.out.println(LocalTime.now() + "UserDAO.addUser(): Error: user already in database");
         return false;
      }

      // Create update statement:
      String query = "INSERT INTO users VALUES(?,?,?,?,?,?,?);";

      boolean commit = false;

      Connection connection = null;
      PreparedStatement stmt = null;

      try{

         connection = db.openConnection();
         stmt = connection.prepareStatement(query);

         stmt.setString(1, user.getUserName());
         stmt.setString(3, user.getPassword());
         stmt.setString(4, user.getEmail());
         stmt.setString(5, user.getFirstName());
         stmt.setString(6, user.getLastName());
         stmt.setString(7, user.getGender());
         stmt.setString(2, user.getPersonId());


         stmt.executeUpdate();
         commit = true;

      }
      catch(DataBaseException message){
         System.out.println(LocalTime.now() + "  UserDao.addUser(): ERROR: " + message.toString());
         throw new DataBaseException(message.toString());
      }
      catch(Exception e){
         System.out.println(LocalTime.now() + "UserDao.addUser(): " + e.toString());
         e.getStackTrace();
         throw new DataBaseException("Something went wrong while trying to add the user");
      }
      finally{
         if(stmt != null){
            try{
               stmt.close();
            }
            catch (Exception e){
               System.out.println(LocalTime.now() + " UserDao.addUser(): ERROR couldn't close prepared statement, " + e.toString());
               throw new DataBaseException("Couldn't add user");
            }
         }
         if(connection != null){
            db.closeConnection(connection, commit);
         }
      }

      System.out.println(LocalTime.now() + " UserDAO.addUser(): User: \"" + user.getUserName() + "\" has been added");
      return true;
   }


   /**
    * Return a user using a user_name.
    * @param user_name: the user to fetch.
    * @return
    * @throws DataBaseException: Contains the message to be returned to the client in case of an error.
    */
   public User getUser(String user_name) throws DataBaseException {
      User usr = null;

      ResultSet result = null;
      Connection connection = null;
      PreparedStatement stmt = null;

      String query = "SELECT * FROM users WHERE user_name = ?";

      try{
         connection = db.openConnection();
         stmt = connection.prepareStatement(query);
         stmt.setString(1, user_name);

         result = stmt.executeQuery();

         if(result.next()){
            System.out.println(LocalTime.now() + " UserDAO.getUser(): User has been found");

            usr = new User(result.getString("user_name"),
                    result.getString("password"),
                    result.getString("email"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("gender"),
                    result.getString("person_id"));
         }
         if(usr != null){
            System.out.println(LocalTime.now() + " UserDAO.getUser() : Fetched user: \"" + usr.getUserName() + "\"");
         }
         return usr;

      }
      catch(DataBaseException message){
         System.out.println(LocalTime.now() + " UserDao.getUsers(): " + message.toString());
         throw new DataBaseException(message.toString());
      }
      catch(Exception e){
         System.out.println(LocalTime.now() + " UserDAO.getUsers(): ERROR while deleting data from users: " + e.toString());
         throw new DataBaseException("Error while deleting data from users table");
      }
      finally {
         if(result != null){
            try{
               stmt.close();
            }
            catch (Exception e){
               System.out.println(LocalTime.now() + " UserDao.getUser(): ERROR couldn't close prepared statement, " + e.toString());
               throw new DataBaseException("Couldn't get user");
            }
         }
         if(stmt != null){
            try{
               stmt.close();
            }
            catch (Exception e){
               System.out.println(LocalTime.now() + " UserDao.getUser(): ERROR couldn't close prepared statement, " + e.toString());
               throw new DataBaseException("Couldn't get user");
            }
         }
         if(connection != null){
            db.closeConnection(connection, false);
         }
      }
   }

   /**
    * Create a token and use it to connect the user.
    * @param   user_name: the user to connect.
    * @param   password: the user's password.
    * @return  the user as a User object. Will return null if the user is not connected.
    */
   public User connectUser(String user_name, String password) throws DataBaseException {
      User user = null;

      // Check if user is registered
      user = getUser(user_name);
      if(user == null){
         System.out.println(LocalTime.now() + "UserDAO.connectUser(): Error: user not registered");
         throw new DataBaseException("The user is not registered");
      }




      return null;
   } // NOT FINISHED: NEED AUTHTOKEN DAO TO BE IMPLEMENTED


   public boolean deleteUsers() throws  DataBaseException{

      Connection connection = null;
      PreparedStatement stmt = null;
      boolean commit = false;

      try{
         connection = db.openConnection();
         String query = "DELETE FROM users;";
         stmt = connection.prepareStatement(query);
         stmt.executeUpdate();
         commit = true;
      }
      catch(DataBaseException message){
         System.out.println(LocalTime.now() + " UserDao.deleteUsers(): " + message.toString());
         throw new DataBaseException(message.toString());
      }
      catch(Exception e){
         System.out.println(LocalTime.now() + " UserDAO.deleteUsers(): ERROR while deleting data from users: " + e.toString());
         throw new DataBaseException("Error while deleting data from users table");
      }
      finally {
         if(stmt != null){
            try{
               stmt.close();
            }
            catch (Exception e){
               System.out.println(LocalTime.now() + " UserDao.deleteUser(): ERROR couldn't close prepared statement, " + e.toString());
               throw new DataBaseException("Couldn't add user");
            }
         }
         if(connection != null){
            db.closeConnection(connection, commit);
         }
      }

      System.out.println(LocalTime.now() + " UserDao.deleteUser(): data in users table cleared");
      return true;
   }

}