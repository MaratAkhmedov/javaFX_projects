/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iconos;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author igugl
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ToggleGroup grupo;
    @FXML
    private Label mensajes;
    @FXML
    private Button amazonButton;
    @FXML
    private Button bloggerButton;
    @FXML
    private Button ebayButton;
    @FXML
    private Button facebookButton;
    @FXML
    private Button googleButton;
    @FXML
    private MenuItem salirItem;
    @FXML
    private MenuItem amazonItem;
    @FXML
    private MenuItem bloggerItem;
    @FXML
    private MenuItem ebayItem;
    @FXML
    private MenuItem facebookItem;
    @FXML
    private MenuItem googleItem;
    @FXML
    private RadioMenuItem radioItemAmazon;
    @FXML
    private RadioMenuItem radioItemEbay;
    
    List<String> blogs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogs = new ArrayList<>();
        blogs.add("El blog de Athos");
        blogs.add("El blog de Porthos");
        blogs.add("El blog de Aramis");
    }    

    @FXML
    private void salir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION); 
        alert.setTitle("Confirmación"); 
        alert.setHeaderText("Vas a salir del programa");
        alert.setContentText("¿Seguro que quieres salir?"); 
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
           Platform.exit();
        } else { 
            System.out.println("CANCEL"); 
        }
    }

    @FXML
    private void amazon(ActionEvent event) {
        if(radioItemAmazon.isSelected()){
            Alert alert = new Alert(AlertType.INFORMATION); 
            alert.setTitle("Confirmación"); 
            alert.setHeaderText("Compra realizada correctamente"); 
            alert.setContentText("Has comprado en Amazon"); 
            alert.showAndWait();
        }else{
            Alert alert = new Alert(AlertType.INFORMATION); 
            alert.setTitle("Error en la selección"); 
            alert.setHeaderText("No puede comprar en Amazon"); 
            alert.setContentText("Por favor, cambia la selección actual en el menu Opciones"); 
            alert.showAndWait();
        }
    }

    @FXML
    private void blogger(ActionEvent event) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("El blog de Athos", blogs); 
        dialog.setTitle("Selecciona un blog"); 
        dialog.setHeaderText("¿Qué blog quieres visitar?"); 
        dialog.setContentText("Elige:"); 
        Optional<String> result = dialog.showAndWait(); 
        // Pre Java 8 
        if (result.isPresent()) { 
            mensajes.setText("Visitando el blog de " + result.get());
        }
    }

    @FXML
    private void ebay(ActionEvent event) {
        if(radioItemEbay.isSelected()){
            Alert alert = new Alert(AlertType.INFORMATION); 
            alert.setTitle("Confirmación"); 
            alert.setHeaderText("Compra realizada correctamente"); 
            alert.setContentText("Has comprado en Ebay"); 
            alert.showAndWait();
        }else{
            Alert alert = new Alert(AlertType.INFORMATION); 
            alert.setTitle("Error en la selección"); 
            alert.setHeaderText("No puede comprar en Ebay"); 
            alert.setContentText("Por favor, cambia la selección actual en el menu Opciones"); 
            alert.showAndWait();
        }
    }

    @FXML
    private void facebook(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("Pepe"); 
        dialog.setTitle("Introduce tu nombre"); 
        dialog.setHeaderText("¿Con qué usuario quieres escribir en Favebook?"); 
        dialog.setContentText("Introduce tu nombre:"); 
        Optional<String> result = dialog.showAndWait(); 
        if (result.isPresent()) { 
            mensajes.setText("Mensaje enviado como " + result.get());
        } 
    }

    @FXML
    private void google(ActionEvent event) {
    }
    
}
