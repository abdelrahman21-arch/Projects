/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assitant;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.ui.listbook.BookListController.Book;
import library.assitant.databse.DatabaseHandler;

/**
 *
 * @author Boody
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private JFXButton label;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton label1;
    
    DatabaseHandler databasehandler;
    @FXML
    private AnchorPane rootPane;
   
    
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            databasehandler= new DatabaseHandler(); // TODO
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkData();
    }    

    @FXML
    private void addBook(ActionEvent event) {
        String bookTitle= title.getText();
        String bookID= id.getText();
        String bookAuthor= author.getText();
        String bookPublisher = publisher.getText();
        if(bookID.isEmpty()||bookTitle.isEmpty()||bookAuthor.isEmpty()||bookPublisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Fields. ");
            alert.showAndWait();
            return;}
          /*stmt.execute(TableName+"Create Table"+"("
            +"      id varchar(220) primarykey \n  "
            +"      title varchar(220) \n    "
            +"      author varchar(220) \n   "
            +"publisher varchar(100) \n"
            +" isAvail boolean default true"+")");*/
        String act;
        act = "INSERT INTO BOOK VALUES ("+
                "'"+bookID+"',"+
                "'"+bookTitle+"',"+
                "'"+bookAuthor+"',"+
                "'"+bookPublisher+"',"+
                "'" + true+ "'"+
                ")";
        System.out.println(act);
      if (databasehandler.ActionExecution(act)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Done.");
            alert.showAndWait();
            
          
      }
      else { //Error
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Operation failed");
            alert.showAndWait();
           
          
      }
      
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = databasehandler.QueryExecution(qu); 
        try {
            while(rs.next()){
                String titlex = rs.getString("title");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}



