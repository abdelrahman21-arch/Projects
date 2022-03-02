package library.assistant.ui.Addmember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assitant.databse.DatabaseHandler;

/**
 *
 * @author mcc2
 */
public class Member_Controller implements Initializable {
    
    DatabaseHandler handler ; 
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane rootPane;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            handler= DatabaseHandler.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(Member_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
      @FXML
    private void addmember(ActionEvent event) { 
        
        String mName = name.getText(); 
        String mID = id.getText();
        String mMobile = mobile.getText(); 
        String mEmail = email.getText();  
        
        Boolean flag = mName.isEmpty() || mID.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty() ; 
        if (flag) {
        Alert alert = new Alert ( Alert.AlertType.ERROR ) ; 
         alert.setHeaderText(null); 
         alert.setContentText("enter all fields"); 
         alert.showAndWait();
         return;  
        }  
        
        
        String st;
        st = "INSERT INTO MEMBERS VALUES ("+
                "'"+mID+"',"+
                "'"+mName+"',"+
                "'"+mMobile+"',"+
                "'"+mEmail+"',"+
                ")";     
             
        System.out.println(st);  
        
        if ( handler.ActionExecution(st)) { 
            
            Alert alert = new Alert ( Alert.AlertType.INFORMATION ) ; 
         alert.setHeaderText(null); 
         alert.setContentText("Saved"); 
         alert.showAndWait();
        } else { 
            
            Alert alert = new Alert ( Alert.AlertType.ERROR ) ; 
         alert.setHeaderText(null); 
         alert.setContentText("Error"); 
         alert.showAndWait();
            
            
        
        }

               
               
        
        
    
    }


    @FXML
    private void cancel(ActionEvent event) {
           Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

  
    
}