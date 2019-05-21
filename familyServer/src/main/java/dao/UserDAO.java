package dao;

import models.User;
import java.time.LocalTime;
import java.sql.*;

/**
 * Perform database operation for users
 */ 
public class UserDAO{
   
   public UserDAO(){}

   public static boolean testConDB(){

      return false;
   }

   /**
    * Add a user to the database. Create a String with the query and pass it to the select method in OperationDAO
    * 
    * @param    user to add
    * @return   False if the username is already used.
    */
   public static boolean addUser(User user) throws DataBaseException {
      // Check if user is registered.
      User tmp_user = isRegistered(user.getUserName());
      if(tmp_user != null){
         System.out.println(LocalTime.now() + "UserDAO.addUser(): Error: user already in database");
         return false;
      }

      // Create update statement:

      String query = "INSERT INTO users VALUES(?,?,?,?,?,?,?);";

      try(Connection connection = OperationDAO.openConnection();
         PreparedStatement stmt = connection.prepareStatement(query))
      {
         stmt.setString(1, user.getUserName());
         stmt.setString(2, user.getPersonId());
         stmt.setString(3, user.getPassword());
         stmt.setString(4, user.getEmail());
         stmt.setString(5, user.getFirstName());
         stmt.setString(6, user.getLastName());
         stmt.setString(7, user.getGender());

         stmt.executeUpdate();

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

      return true;
   }
   
   /**
    * Create a token and use it to connect the user.
    * @param   user_name: the user to connect.
    * @param   password: the user's password.
    * @return  the user as a User object. Will return null if the user is not connected.
    */
   public static User connectUser(String user_name, String password) throws DataBaseException {
      
      return null;
   }
   
   /**
    * Check if a user is registered in the databse (registered, not connected).
    * @param   user_name: the user to check.
    * @return  the user as a User object. Will be null if the user is not found/registered.
    */
   public static User isRegistered(String user_name) throws DataBaseException {
      
      return null;
   }
   
    
    
}