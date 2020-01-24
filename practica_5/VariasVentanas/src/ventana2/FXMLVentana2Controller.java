/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class FXMLVentana2Controller implements Initializable {
    
    private int contador_ventanas=3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crearVentana3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/ventana3/FXMLVentana3.fxml"));

        Scene scene = new Scene(root);
        Stage ventana3 = new Stage();
        ventana3.setTitle("Ventana NO MODAL ("+ Integer.toString(contador_ventanas)+")");
        contador_ventanas++;
        ventana3.initModality(Modality.NONE);
        ventana3.setScene(scene);
        ventana3.show();
    }

}
