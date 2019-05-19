package dao;

import models.User;
/**
 * Perform database operation for users
 */ 
public class UserDAO{
   
   public UserDAO(){}

   public static boolean testConDB(){

      return false;
   }

   /**
    * Add a user to the databse
    * Create a String with the query and pass it to the select method in OperationDAO
    * 
    * @param    user to add
    * @return   False if the username is already used.
    */
   public static boolean addUser(User user) throws DataBaseException {
       
       return false;
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