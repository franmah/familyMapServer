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

    ** If the creation of the database fail, the familyDB.db should be deleted.
    */



/**
 * OperationDAO is used by the other DAO classes.
 * It receives queries as String, open connections, execute queries and close connections.
 */
public class OperationDAO{

    private final String CURR_DIR = System.getProperty("user.dir");
    private final String DRIVER = "org.sqlite.JDBC";
    private final String DB_TYPE = "jdbc:sqlite:";
    // CURR_DIR doesn't give the file's path
    //private final String DB_LOCATION = String.format(CURR_DIR + File.separator + ".." + File.separator + "database" + File.separator + "familyServerDB.db");
    private final String DB_LOCATION = ("C:\\Users\\Francois\\AndroidStudioProjects\\fma\\familyServer\\src\\main\\java\\database\\familyServerDB.db");


    public OperationDAO(){}


    /**
     * Create the database and execute CREATE commands.
     * Use the createQueryFromFile method to create a query as a String from initializeDataBase.sql.txt
     */
    public Connection createDataBase() throws DataBaseException{

        final String CREATE_TABLES_QUERY = ("C:\\Users\\Francois\\AndroidStudioProjects\\fma\\familyServer\\src\\main\\java\\SQLcode\\InitializeDataBase.sql.txt");
        //final String CREATE_TABLES_QUERY = CURR_DIR + File.separator +".." + File.separator + "SQLcode" + File.separator + "initializeDataBase.sql.txt";

        Connection connection = null;
        Statement stmt = null;
        try{

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_TYPE + DB_LOCATION);
            System.out.println("Data Base file has been created");

            // Create TABLES from sql file.
            String query = createQueryFromFile(CREATE_TABLES_QUERY);
            //System.out.println(query);

            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("OperationDAO.createDataBase(): Tables have been created with success!");

            // FIX IT:
            stmt.close();
            return connection;

        }
        catch(Exception e){
            System.out.println("OperationDAO.createDataBase(): Error: " + e.toString());

            File new_db = new File(DB_LOCATION);
            if(new_db.delete()){
                System.out.println("The newly created database file has been deleted with success.");
            }
            else{
                System.out.println("The newly created database could not be deleted.");
            }

            throw new DataBaseException("Database couldn't be created");
        }

    }

    /**
     * Create a String from a sql file.
     * The string is used to query a file.
     */
    public String createQueryFromFile(String file_location) throws DataBaseException{

        File file = new File(file_location);
        if(!file.exists()){
            System.out.println("OperationDAO.createQueryFromFile(): Error: the sql file doesn't exist.");
            System.out.println("file path: " + file_location);
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
     * @return   connection: connection used by the two public methods ExecuteUpdate and executeQuery.
     */
    public Connection openConnection() throws DataBaseException{
        Connection connection = null;
        //Test if db exists:
        File db_file = new File(DB_LOCATION);

        try{

            if(!db_file.exists()){
                System.out.println("OperationDAO.openConnection(): WARNING: the data base file doesn't exit, creating file...");
                connection = createDataBase();
                connection.setAutoCommit(false);
                System.out.println("OperationDAO.openConnection(): connection successful.");
                return connection;
            }

            // Open and return connection
            Class.forName(DRIVER);
            System.out.println("CURRENT: " + System.getProperty("user.dir"));
            connection = DriverManager.getConnection(DB_TYPE + DB_LOCATION);
            connection.setAutoCommit(false);
            System.out.println("OperationDAO.openConnection(): connection successful.");
            return connection;
        }
        //catch(DataBaseException message){
            //throw new DataBaseException(message.toString());
        //}
        catch(Exception e){
            System.out.println("OperationDAO.openConnection(): Error: " + e);
            throw new DataBaseException("Connection to the databse failed.");
        }


    }

    /**
     * Close a connection to a database.
     * @param   connection: the connection to close.
     */
    public void closeConnection(Connection connection, boolean commit) throws DataBaseException{

        if(connection != null){
            try{
                if(commit){
                    connection.commit();
                }
                else{
                    connection.rollback();
                }
                connection.close();
                System.out.println("OperationDAO.closeConnection(): connection has been closed.");
            }
            catch(Exception e){
                System.out.println("OperationDAO.closeConnection(): Error: " + e.toString());
                throw new DataBaseException("The connection couldn't be closed.");
            }
        }
    }

    public boolean clearDataBase(){

        return true;
    }

////////////////////////////////////////////////
}