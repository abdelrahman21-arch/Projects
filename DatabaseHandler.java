/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assitant.databse;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import library.assistant.ui.listbook.BookListController.Book;


/**
 *
 * @author Boody
 */
public final class DatabaseHandler {
    private static DatabaseHandler handler;
    private static final String DB_URL="jdbc:derby:database;create=true"; // creates the database 
    private static Connection connection= null;
    private static Statement stmt = null;

   

    public DatabaseHandler() throws SQLException  {
        CreateConnection(); // creates connection 
        seTupBookTable (); //creates the Tables
        seTupMemberTable();
        
    }

   public static DatabaseHandler getInstance() throws SQLException{
       if(handler==null){ 
       handler=new DatabaseHandler();
       
       }
       return handler;
           
   }
     
    
    
  public static void CreateConnection(){
 try{
   Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();  //links the derby.jav folder
   connection=DriverManager.getConnection(DB_URL);} // implements the Driver manager to set the connection
 catch (Exception e){
     e.printStackTrace();
     
   
}
}
  
  
  
void seTupBookTable () throws SQLException{
    String TableName= "BOOK";
    try {
        stmt= connection.createStatement();
        DatabaseMetaData dbmt;
        dbmt = connection.getMetaData();
        ResultSet tables = dbmt.getTables(null, null, TableName.toUpperCase(), null);
        if(tables.next()){
            System.out.println("Table \t "+ TableName + "already exists ready to implement \t");
            
        }
        
        else{
            stmt.execute(" CREATE TABLE " + TableName + "("
            + "      id varchar(200) primary key ,\n  "
            + "      title varchar(200)  ,\n    "
            + "      author varchar(200),\n   "
            + "publisher varchar(100),\n"
            + " isAvail boolean default true"
            + ")");
            
        }}

catch (SQLException e){ 
    
    System.err.println(e.getMessage()+"Database hasnt been created");
        
        }
      
        }
    
    
    



public ResultSet QueryExecution (String query){
     ResultSet result;
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        
        return result;
        
  }
public boolean ActionExecution (String act) {
        try {
            stmt = connection.createStatement();
            stmt.execute(act);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error  has Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        
    }
public boolean deleteBook(Book book) {
        try {
            String deleteStatement = "DELETE FROM BOOK WHERE ID = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteStatement);
            stmt.setString(1, book.getId());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {
            System.out.println("Error");
        }
        return false;
    }
void seTupMemberTable () throws SQLException{
    String TableName= "MEMBERS";
    try {
        stmt= connection.createStatement();
        DatabaseMetaData dbmt;
        dbmt = connection.getMetaData();
        ResultSet tables = dbmt.getTables(null, null, TableName.toUpperCase(), null);
        if(tables.next()){
            System.out.println("Table \t "+ TableName + "already exists ready to implement \t");
            
        }
        
        else{
            stmt.execute(" CREATE TABLE " + TableName + "("
            + "      id varchar(200) primary key ,\n  "
            + "      name varchar(200)  ,\n    "
            + "      mobile varchar(200),\n   "
            + "      email varchar(100),\n   "
            + ")");
            
        }}

catch (SQLException e){ 
    
    System.err.println(e.getMessage()+"Database hasnt been created");
        
        }
      
        }
   
        
        
        
        
        
        
        
        
        
        
}


