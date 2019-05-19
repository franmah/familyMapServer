package dao;

import models.AuthToken;

/**
 * Manage AuthToken in the database:
 * Add tokens and link them to users.
 * Check if a token is linked to a user.
 */
public class AuthTokenDAO{
    
    public AuthTokenDAO(){}
    
    /**
     * Check in the database if a token is linked to a user.
     * @param   token: the user's token.
     * @return  the user_name, will be null if no user found.
     */
    public static String isAuthorizedUser(AuthToken token) throws DataBaseException{
        
        return null;
    }
    
    /**
     * Create a query to add a token with it's user (user_name).
     * @param   token: the token to add.
     * @param   user_name: the user to link to the token.
     * @return  true if the token is added, else return false.
     */
    public static boolean addToken(AuthToken token, String user_name) throws DataBaseException{
        
        return false;
    }
}