/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica_1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author maak
 */
public class FXMLController implements Initializable {

    @FXML
    private TextField textoUsuario;
    @FXML
    private PasswordField contrasenaUsuario;
    @FXML
    private Text mensajeUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pulsadoIniciar(ActionEvent event) {
        mensajeUsuario.setText("Bienvenido: " + textoUsuario.getText() + "\nContraseña: " + contrasenaUsuario.getText());
    }
    
}
