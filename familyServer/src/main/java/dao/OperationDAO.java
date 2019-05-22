package dao;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.time.LocalTime;

import models.AuthToken;


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

    Connection connection = null;
    UserDAO user_dao = null;
    PersonDAO person_dao = null;
    EventDAO event_dao = null;
    AuthTokenDAO autToken_dao = null;

    public OperationDAO(){
        try {
            this.openConnection();
            this.user_dao = new UserDAO(connection);
            this.person_dao = new PersonDAO(connection);
            this.event_dao = new EventDAO(connection);
            this.autToken_dao = new AuthTokenDAO(connection);
        }
        catch (DataBaseException message){
                throw new DataBaseException(message.toString());
        }
        catch (Exception e){
            System.out.println(LocalTime.now() + " OperationDAO.constructor: error while creating OperationDAO object " + e.toString());
            throw new DataBaseException("Internal error: Unable to connect to database");
        }
    }

    public UserDAO getUser_dao() { return user_dao; }

    public PersonDAO getPerson_dao() { return person_dao; }

    public EventDAO getEvent_dao() { return event_dao; }

    public AuthTokenDAO getAutToken_dao() { return autToken_dao; }

    /**
     * Create the database and execute CREATE commands.
     * Use the createQueryFromFile method to create a query as a String from initializeDataBase.sql.txt
     */
    public void createDataBase() throws DataBaseException{

        final String CREATE_TABLES_QUERY = ("C:\\Users\\Francois\\AndroidStudioProjects\\fma\\familyServer\\src\\main\\java\\SQLcode\\InitializeDataBase.sql.txt");
        //final String CREATE_TABLES_QUERY = CURR_DIR + File.separator +".." + File.separator + "SQLcode" + File.separator + "initializeDataBase.sql.txt";

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
            System.out.println(LocalTime.now() + " OperationDAO.createDataBase(): Tables have been created with success!");

            // FIX IT:
            stmt.close();

        }
        catch(Exception e){
            System.out.println(LocalTime.now() + " OperationDAO.createDataBase(): Error: " + e.toString());

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
            System.out.println(LocalTime.now() + " OperationDAO.createQueryFromFile(): Error: the sql file doesn't exist.");
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
            System.out.println(LocalTime.now() + " OperationDAO.createQueryFromFile(): Error while reading the sql file: " + e.toString());
            throw new DataBaseException("Error while reading the sql file");
        }
    }

    /**
     * Open a connection to the database.
     * @return   connection: connection used by the two public methods ExecuteUpdate and executeQuery.
     */
    public void openConnection() throws DataBaseException{
        //Test if db exists:
        File db_file = new File(DB_LOCATION);

        try{

            if(!db_file.exists()){
                System.out.println(LocalTime.now() + " OperationDAO.openConnection(): WARNING: the data base file doesn't exit, creating file...");
                this.createDataBase();
                connection.setAutoCommit(false);
                //System.out.println(LocalTime.now() + " OperationDAO.openConnection(): connection successful.");
            }

            // Open and return connection
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_TYPE + DB_LOCATION);
            connection.setAutoCommit(false);
            System.out.println(LocalTime.now() + " OperationDAO.openConnection(): connection successful.");
        }
        //catch(DataBaseException message){
            //throw new DataBaseException(message.toString());
        //}
        catch(Exception e){
            System.out.println(LocalTime.now() + " OperationDAO.openConnection(): Error: " + e);
            throw new DataBaseException("Connection to the database failed.");
        }


    }

    /**
     * Close a connection to a database.
     * @param   commit: whether to commit or not.
     */
    public void commitAndCloseConnection(boolean commit) throws DataBaseException{

        if(connection != null){
            try{
                if(commit){
                    connection.commit();
                }
                else{
                    connection.rollback();
                }

                connection.close();
                if(commit) {
                    System.out.println(LocalTime.now() + " OperationDAO.closeConnection(): connection has been closed and changes were committed");
                }
                else{
                    System.out.println(LocalTime.now() + " OperationDAO.closeConnection(): connection has been closed and changes were not committed");
                }
            }
            catch(Exception e){
                System.out.println(LocalTime.now() + " OperationDAO.closeConnection(): Error: " + e.toString());
                throw new DataBaseException("The connection couldn't be closed.");
            }
        }
    }



////////////////////////////////////////////////
}