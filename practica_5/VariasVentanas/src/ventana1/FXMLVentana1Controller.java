/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jsoler
 */
public class FXMLVentana1Controller implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void mostrarVentana2(ActionEvent event) throws IOException {

            
        label.setText("VENTANA PRINCIPAL");
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/ventana2/FXMLVentana2.fxml"));

        Scene scene = new Scene(root);
        Stage ventana2= new Stage();
        ventana2.setTitle("Ventana MODAL (2)");
        ventana2.initModality(Modality.APPLICATION_MODAL);
        ventana2.setScene(scene);
        ventana2.showAndWait();
        label.setText("Ventana modal cerrada");
    }
    
}
