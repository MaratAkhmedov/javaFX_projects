/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author maak
 */
public class FXMLPersonaController implements Initializable {
    private boolean nuevaPersona = false;
    private Persona persona;
    @FXML
    private TextField nombreTF;
    @FXML
    private TextField apellidoTF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addPersona(ActionEvent event) {
        if ((!nombreTF.getText().isEmpty())
                && (nombreTF.getText().trim().length() != 0)
                && (!apellidoTF.getText().isEmpty())
                && (apellidoTF.getText().trim().length() != 0)) {
            persona = new Persona(nombreTF.getText(), apellidoTF.getText());
            nombreTF.clear();
            apellidoTF.clear();
            nuevaPersona = true;
            ((Stage)nombreTF.getScene().getWindow()).close();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage)nombreTF.getScene().getWindow()).close();
    }
    
    public boolean hayPersona(){
        return true;
    }
    
    public Persona getPersona(){
        return persona;
    }
}
