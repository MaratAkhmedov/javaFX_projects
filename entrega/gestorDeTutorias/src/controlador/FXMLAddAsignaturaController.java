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
import modelo.Asignatura;

/**
 * FXML Controller class
 *
 * @author igugl
 */
public class FXMLAddAsignaturaController implements Initializable {
    @FXML
    private TextField codigoField;
    @FXML
    private TextField descripcionField;
    @FXML
    private Button anyadirButton;
    @FXML
    private Button cancelarButton;
    
    private Asignatura asignatura;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                anyadirButton.disableProperty().bind(codigoField.textProperty().isEmpty().or(descripcionField.textProperty().isEmpty()));

    }    

    @FXML
    private void anyadir(ActionEvent event) {
        String codigo = codigoField.textProperty().get();
        String descripcion = descripcionField.textProperty().get();
        asignatura = new Asignatura(codigo, descripcion);
        ((Stage)codigoField.getScene().getWindow()).close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage)codigoField.getScene().getWindow()).close();   
    }
    
    public Asignatura getAsignatura(){
        return asignatura;
    }
    
}
