/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pasodatosventana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jose
 */
public class FXMLVentana1Controller implements Initializable {

    @FXML
    private Button button;
    @FXML
    private TextField text2ventana2;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         button.disableProperty().bind(text2ventana2.textProperty().isEmpty());
    }    
    @FXML
    private void mostrarVentana(ActionEvent event) {
        try {
            
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLVentana2.fxml"));
            Parent root = (Parent) cargador.load();
            
            //obtenemos la referencia del controlador par poder invocar el metodo publico initText()
            FXMLVentana2Controller controlador = cargador.getController();
            
            controlador.initText(text2ventana2.getText());
            
            Scene scene = new Scene(root);

            Stage ventana2 = new Stage();
            ventana2.setTitle("Ventana NO MODAL");
            ventana2.initModality(Modality.NONE);
            ventana2.setScene(scene);
            ventana2.show();
  
        } catch (IOException ex) {
            Logger.getLogger(FXMLVentana1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
