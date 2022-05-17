/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sin11368;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import prog24178.labs.objects.CookieInventoryItem;
import prog24178.labs.objects.Cookies;

/**
 * FXML Controller class
 *
 * @author Gurveer
 * @version 1.0
 */
public class CookieFXMLController implements Initializable {
    
    CookieInventoryFile cookieFile = new CookieInventoryFile();
    @FXML
    public ComboBox<Cookies> cmbCookies;
    @FXML
    public Button btnsell,btnadd,btnexit;
    @FXML
    public TextField sqty, aqty;
    
    // Inner class for defining the functioning of the buttons
    public class Action implements EventHandler<ActionEvent>{
        
        public void action(ActionEvent e){
            
            if(e.getSource()==btnsell){
                
                try{
                    int sellqty = Integer.parseInt(sqty.getText());
                    if(sellqty<0){
                        throw new IllegalArgumentException();
                    }else if(sqty.getText()==null){
                        throw new NullPointerException();
                    }else{
                        Cookies select = cmbCookies.getValue();
                        try{
                            File file = new File("Cookie.txt");
                            cookieFile.loadFromFile(file);
                            CookieInventoryItem found = cookieFile.find(select.getId());
                            if(found != null){
                                if(sellqty<=found.getQuantity()){
                                    int newQty = found.getQuantity() - sellqty;
                                    found.setQuantity(newQty);
                                    sqty.clear();
                                }else{
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Data Entry Error");
                                    alert.setContentText("Sorry not Enough " + select.getName() 
                                    + " to sell you only have " + found.getQuantity() + " left.");
                                    alert.showAndWait();
                                }
                            }else{
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Insufficient Inventory");
                                alert.setContentText("Sorry there are no more " + select.getName() 
                                + " avaliable to sell");
                                alert.showAndWait();
                            }
                            
                            
                        }catch(FileNotFoundException i){
                            //
                        }
                    }
                    
                }catch(NullPointerException i){
                    emptyNumber();
                        
                }catch(NumberFormatException i){
                    illegalNumber();
                    
                }catch (IllegalArgumentException i){
                    negativeNumber();
                }
                
                
            }else if(e.getSource()==btnadd){
                try{
                    int addqty = Integer.parseInt(aqty.getText());
                    if(addqty<0){
                        throw new IllegalArgumentException();      
                    }else if(aqty.getText()==null){
                        throw new NullPointerException();
                    }else{
                        Cookies select = cmbCookies.getValue();
                        cookieFile.add(new CookieInventoryItem(select, addqty));
                        aqty.clear();
                    }
                }catch(NullPointerException i){
                    emptyNumber();
                        
                }catch(NumberFormatException i){
                    illegalNumber();
                        
                }catch(IllegalArgumentException i){
                    negativeNumber();
                    
                }
            }else if(e.getSource()==btnexit){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you wish to exit?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Exit Program");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you wish to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()== ButtonType.YES){
                    File file = new File("cookies.txt");
                    cookieFile.writeFromFile(file);
                    System.exit(0);
                }
                    
            }
        }

        @Override
        public void handle(ActionEvent t) {
            action(t);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> cookieList = FXCollections.observableArrayList();
        cmbCookies.getItems().setAll(Cookies.values());
        btnsell.setOnAction(new Action());
        btnadd.setOnAction(new Action());
        btnexit.setOnAction(new Action());
        // TODO
    }    
    
    /**
     * negative number function to give the alert box if the value entered in the text box
     * is less than 0
     */
    public void negativeNumber(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Data Entry Error");
        alert.setHeaderText("Please enter the Number more than 0.");
        alert.showAndWait();
    }
    
    /**
     * Alert box function to give the alert box if the text box is empty
     */
    public void emptyNumber(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Data Entry Error");
        alert.setHeaderText("Please enter the number of cookies sold.");
        alert.showAndWait();
    }
    
    /**
     * illegalNumber function gives alert box if the entered value is not an Integer value
     */
    public void illegalNumber(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Data Entry Error");
        alert.setHeaderText("You must enter a valid numeric value.");
        alert.showAndWait();
    }
}
