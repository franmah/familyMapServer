package dao;

import models.AuthToken;
import java.time.LocalTime;
import java.sql.*;

/**
 * Manage AuthToken in the database:
 * Add tokens and link them to users.
 * Check if a token is linked to a user.
 */
public class AuthTokenDAO{
    private Connection connection = null;
    
    public AuthTokenDAO(){
    }

    public AuthTokenDAO(Connection connection){ this.connection = connection; }
    
    /**
     * Check in the database if a token is linked to a user.
     * @param   token: the user's token.
     * @return  the user_name, will be null if no user found.
     */
    public boolean isConnected(AuthToken token) throws DataBaseException{
        ResultSet result = null;
        PreparedStatement stmt = null;

        String query = "SELECT * FROM authtokens WHERE token = ?"; // Look for a token because there can be multiple same user but only one token.

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, token.getToken());
            result = stmt.executeQuery();

            String user_name = null;

            if(result.next()){
                System.out.println(LocalTime.now() + " AuthTokenDAO.isConnected(): A pair user/token has been found");
                user_name = result.getString("user_name");
            }
            else{
                System.out.println(LocalTime.now() + " AuthToken.isConnected(): No user found for this token");
                return false;
            }

            if(user_name.equals(token.getUser())){
                return true;
            }
            else{
                return false;
            }

        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " AuthTokenDao.isConnected(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " AuthTokenDAO.isConnected(): ERROR while retrieving data: " + e.toString());
            throw new DataBaseException("Error while retrieving token from database");
        }
        finally {
            if(result != null){
                try{
                    result.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " AuthTokenDAO.isConnected(): ERROR Unable to close resultSet, " + e.toString());
                    throw new DataBaseException("Unable to retrieve token from database");
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " AuthTokenDAO.isConnected(): ERROR Unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("Unable to retrieve token from database");
                }
            }
        }
    }
    
    /**
     * Add a token with it's user (user_name). Assume the user has already been checked for registration.
     * @param   token: the token to add.
     * @return  true if the token is added, else return an error.
     */
    public boolean addToken(AuthToken token) throws DataBaseException{
        // Create update statement:
        String query = "INSERT INTO authtokens VALUES(?,?);";

        PreparedStatement stmt = null;

        try{

            stmt = connection.prepareStatement(query);

            stmt.setString(1, token.getToken());
            stmt.setString(2, token.getUser());

            stmt.executeUpdate();

            System.out.println(LocalTime.now() + " AuthTokenDao.addToken(): Token for the user: \"" + token.getUser() + "\" has been added");
            return true;
        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + "  AuthTokenDao.addToken(): ERROR: " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + "AuthTokenDao.addToken(): " + e.toString());
            e.getStackTrace();
            throw new DataBaseException("Something went wrong while connecting the user");
        }
        finally{
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " AuthTokenDao.addToken(): ERROR Unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("Unable to connect user");
                }
            }
        }

    }

    public boolean deleteTokens() throws  DataBaseException{

        PreparedStatement stmt = null;

        try{
            String query = "DELETE FROM authtokens;";
            stmt = connection.prepareStatement(query);
            stmt.executeUpdate();

            System.out.println(LocalTime.now() + " AuthTokenDAO.deleteTokens(): data in authtokens table cleared");
            return true;
        }
        catch(DataBaseException message){
            System.out.println(LocalTime.now() + " AuthTokenDAO.deleteTokens(): " + message.toString());
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " AuthTokenDAO.deleteTokens(): ERROR while deleting data from authtokens: " + e.toString());
            throw new DataBaseException("Error while deleting data from authtokens table");
        }
        finally {
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (Exception e){
                    System.out.println(LocalTime.now() + " AuthTokenDAO.deleteTokens(): ERROR unable to close prepared statement, " + e.toString());
                    throw new DataBaseException("Unable to delete tokens");
                }
            }
        }

    }
}