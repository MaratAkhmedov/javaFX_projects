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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class FXMLPersonaController implements Initializable {

    Persona personaNueva = null;
    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidosText;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void okPersona(ActionEvent event) {
        if(personaNueva == null){
            personaNueva = new Persona(nombreText.getText(), apellidosText.getText());
        }else{
            personaNueva.setNombre(nombreText.getText());
            personaNueva.setApellidos(apellidosText.getText());
        }
        ((Stage)nombreText.getScene().getWindow()).close();
    }

    @FXML
    private void cancelarPersona(ActionEvent event) {
        ((Stage)nombreText.getScene().getWindow()).close();     
    }
 
    public Persona getPersona(){
        return personaNueva;
    }
    
    public void setPerson(Persona p){
        personaNueva = p;
        nombreText.setText(p.getNombre());
        apellidosText.setText(p.getApellidos());
    }
    
}
