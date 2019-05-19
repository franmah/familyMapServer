package dao;

import java.sql.*;
import java.io.*;
import java.util.*;

/*
once working:
    take care of DataBaseException.
    add a catch that will return any DataBaseException as thrown

    exemple for creating databse:

    try{
        createdb
    }
    catch(DataBaseException message){
        throw new DataBaseException(message);
    }
    catch(Exception e){
        throw new DataBaseException("something went wrong while create db")
    }


    ** in createDataBase() fix the close issue for stmt and connection, they should be closed no matter what


    */



/**
 * OperationDAO is used by the other DAO classes.
 * It receives queries as String, open connections, execute queries and close connections.
 */
public class OperationDAO{

    private static final String CURR_DIR = System.getProperty("user.dir");
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_TYPE = "jdbc:sqlite:";
    private static final String DB_LOCATION = String.format(CURR_DIR + File.separator + ".." + File.separator + "database" + File.separator + "familyServerDB.db");
    private static final String CREATE_TABLES_QUERY = CURR_DIR + File.separator +".." + File.separator + "SQLcode" + File.separator + "initializeDataBase.sql.txt";

    public OperationDAO(){}


    /**
     * Create the database and execute CREATE commands.
     * Use the createQueryFromFile method to create a query as a String from initializeDataBase.sql.txt
     */
    private static Connection createDataBase() throws DataBaseException{
        Connection connection = null;
        Statement stmt = null;
        try{

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_TYPE + DB_LOCATION);
            System.out.println("Data Base file has been created");

            // Create TABLES from sql file.
            String query = createQueryFromFile(CREATE_TABLES_QUERY);

            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("OperationDAO.createDataBase(): Tables have been created with success!");

            // FIX IT:
            stmt.close();
            return connection;

        }
        catch(Exception e){
            System.out.println("OperationDAO.createDataBase(): Error: " + e);
            throw new DataBaseException("Database couldn't be created");
        }

    }

    /**
     * Create a String from a sql file.
     * The string is used to query a file.
     */
    public static String createQueryFromFile(String file_location) throws DataBaseException{

        File file = new File(file_location);
        if(!file.exists()){
            System.out.println("OperationDAO.createQueryFromFile(): Error: the sql file doesn't exist.");
            throw new DataBaseException("SQL file doesn't exist");
        }

        try(Scanner sc = new Scanner(new File(file_location));){

            StringBuilder createStatement = new StringBuilder();

            while(sc.hasNext()){
                createStatement.append(sc.nextLine());
            }

            return createStatement.toString();
        }
        catch(Exception e){
            System.out.println("OperationDAO.createQueryFromFile(): Error while reading the sql file: " + e.toString());
            throw new DataBaseException("Error while reading the sql file");
        }
    }

    /**
     * Open a connection to the database.
     * @param   connection: connection used by the two public methods ExecuteUpdate and executeQuery.
     */
    private static void openConnection(Connection connection) throws DataBaseException{

        //Test if db exists:
        File db_file = new File(DB_LOCATION);

        try{

            if(!db_file.exists()){
                System.out.println("OperationDAO.openConnection(): WARNING: the data base file doesn't exit, creating file...");
                connection = createDataBase();
                connection.setAutoCommit(false);
                System.out.println("OperationDAO.openConnection(): connection successful.");
                return;
            }

            // Open and return connection

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_TYPE + DB_LOCATION);
            connection.setAutoCommit(false);
            System.out.println("OperationDAO.openConnection(): connection successful.");

        }
        catch(DataBaseException message){
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println("OperationDAO.openConnection(): Error: " + e);
            throw new DataBaseException("Database couldn't be created.");
        }


    }

    /**
     * Close a connection to a database.
     * @param   connection: the connection to close.
     */
    private static void closeConnection(Connection connection) throws DataBaseException{

        if(connection != null){
            try{
                connection.close();
                System.out.println("OperationDAO.closeConnection(): connection has been closed.");
            }
            catch(Exception e){
                System.out.println("OperationDAO.closeConnection(): Error: " + e.toString());
                throw new DataBaseException("The connection couldn't be closed.");
            }
        }
    }


    /**
     * Execute any type query that require an "executeUpdate()" command.
     * @param   query: the query to execute.
     */
    public static void executeUpdate(String query) throws DataBaseException{
        Connection connection = null;

        try{
            openConnection(connection);

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();

            connection.commit();
        }
        catch(DataBaseException message){
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println("OperationDAO.executeUpdate(): ERROR: couldn't execute update: " + e.toString());
            throw new DataBaseException("Couldn't execute update statement");
        }
        finally{
            closeConnection(connection);
        }

    }

    /**
     * Execute a SELECT type query on the databse.
     * @param   query: the query to execute.
     * @return   ResultSet from the SELECT.
     */
    public static ResultSet executeQuery(String query) throws DataBaseException{
        Connection connection = null;

        try{
            openConnection(connection);

            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            stmt.close();

            return result;
        }
        catch(DataBaseException message){
            throw new DataBaseException(message.toString());
        }
        catch(Exception e){
            System.out.println("OperationDAO.executeUpdate(): ERROR: couldn't execute select: " + e.toString());
            throw new DataBaseException("Couldn't execute select statement");
        }
        finally{
            closeConnection(connection);
        }
    }


}