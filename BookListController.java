/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.listbook;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.assitant.FXMLDocumentController;
import library.assitant.databse.DatabaseHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class BookListController implements Initializable {


    ObservableList<Book> list = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availabilityCol;
    @FXML
    private JFXButton label2;
    @FXML
    private JFXButton label3;
   
   @Override
    public void initialize(URL location, ResourceBundle resources) {
         initCol();
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  


    private void initCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
    }

    private void loadData() throws SQLException {
       list.clear();
        DatabaseHandler  handler = new DatabaseHandler ();
           
        String qu = "SELECT * FROM BOOK";
        ResultSet rs = handler.QueryExecution(qu); 
        try {
            while(rs.next()){
                String title = rs.getString("title");
                String author = rs.getString("id");
                String id = rs.getString("author");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");
               list.add(new Book(title,author,id,publisher,avail)); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            // TODO
        
          
       tableView.getItems().setAll(list);
       
    }
    @FXML 
    private void deleteselectedBook(ActionEvent event) throws SQLException{
        // fetch selectedrow
        Book selecttodelete=tableView.getSelectionModel().getSelectedItem();
        if(selecttodelete==null){ 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("select a book to delete");
            alert.showAndWait();
        return;}
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure want to delete the book " + selecttodelete.getTitle() + " ?");
        Optional<ButtonType> answer = alert.showAndWait(); 
        if(answer.get()==ButtonType.OK){
            Boolean result= DatabaseHandler.getInstance().deleteBook(selecttodelete);
            if(result){
            Alert alert1 = new Alert(AlertType.INFORMATION);
        alert1.setTitle("Book deleted");
        alert1.setHeaderText(null);
        alert1.setContentText(selecttodelete.getTitle() +"Delted successfully");
        alert1.showAndWait();
        list.remove(selecttodelete); 
        
            }
            else{
                Alert alert2= new Alert(AlertType.INFORMATION);
                alert2.setTitle("Failed");
                alert2.setHeaderText(null);
                alert2.setContentText(selecttodelete.getTitle()+"Couldnt be deleted");
                alert2.showAndWait();
            }
        
        }
        else{
                Alert alert3= new Alert(AlertType.INFORMATION);
                alert3.setTitle(" Deletion Canceled");
                alert3.setHeaderText(null);
                alert3.setContentText(selecttodelete.getTitle()+"Deletion Process Cancelled");
                alert3.showAndWait();
            
        }
            
          }
     @FXML
    private void handleRefresh(ActionEvent event) throws SQLException {
        loadData(); 
    }
 

 



 
  
    
  public static class Book {
      private final SimpleStringProperty title;
      private final SimpleStringProperty id;
      private final SimpleStringProperty author;
      private final SimpleStringProperty publisher;
      private SimpleBooleanProperty availability;

        public Book(String title, String id, String author, String publisher, Boolean avail) {
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.availability = new SimpleBooleanProperty(avail);
           
        }

       

    
        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public void setAvailiability( Boolean avail) {
            this.availability= availability;
        }
      
      
      
      
  }
    
}
