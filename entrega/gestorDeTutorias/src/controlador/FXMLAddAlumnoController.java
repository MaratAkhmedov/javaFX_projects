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
import modelo.Alumno;

/**
 * FXML Controller class
 *
 * @author igugl
 */
public class FXMLAddAlumnoController implements Initializable {
    
    @FXML
    private TextField mailField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidosField;
    @FXML
    private Button anyadirButton;
    @FXML
    private Button cancelarButton;
    Alumno alumno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        anyadirButton.disableProperty().bind(mailField.textProperty().isEmpty().or(nombreField.textProperty().isEmpty().or(apellidosField.textProperty().isEmpty())));
    }    

    @FXML
    private void anyadir(ActionEvent event) {
        String mail = mailField.textProperty().get();
        String apellidos = apellidosField.textProperty().get();
        String nombre = nombreField.textProperty().get();
        alumno = new Alumno(nombre, apellidos, mail);
        ((Stage)mailField.getScene().getWindow()).close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage)mailField.getScene().getWindow()).close();   

    }
    
    public Alumno getAlumno(){
        return alumno;
    }
}
