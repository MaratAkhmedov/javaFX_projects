/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temporizadorintervalos;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.StyleableBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import modelo.Sesion;
import javafx.application.Platform;

/**
 *
 * @author jsoler
 */
public class FXMLTemporizadorController implements Initializable {
    BooleanProperty parado= new SimpleBooleanProperty(false);
    BooleanProperty iniciado= new SimpleBooleanProperty(false);
        
    private IntervalTimerS servicio;
    private Sesion sesionPrueba;
    
    @FXML
    private Text textoTiempo;   
    @FXML
    private Button iniBut;
    @FXML
    private Button reaBut;
    @FXML
    private Button parBut;
    @FXML
    private Button sigBut;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable de los buttons
        iniBut.disableProperty().bind(iniciado);
        parBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), parado));
        reaBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), Bindings.not(parado)));
        sigBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), Bindings.not(parado)));
        
        sesionPrueba = new Sesion();
        sesionPrueba.setN_circuitos(2);
        sesionPrueba.setDescanso_circuito(Duration.ofSeconds(5));
        sesionPrueba.setN_ejercicios(3);
        sesionPrueba.setT_ejercicio(Duration.ofSeconds(7));
        sesionPrueba.setDescanso_ejercicio(Duration.ofSeconds(10));

        servicio = new IntervalTimerS();
        servicio.setSesionTipo(sesionPrueba);
        textoTiempo.textProperty().bind(servicio.tiempoProperty());
        
        servicio.setOnSucceeded(c -> {
            if (servicio.getValue()) {
                Alert alertaFinal = new Alert(Alert.AlertType.INFORMATION);
                alertaFinal.setContentText("ESTO HA TERMINADO");
                alertaFinal.showAndWait();
                Platform.exit();
            }
        });

        // TODO
    }    

    @FXML
    private void iniciar(ActionEvent event) {
        servicio.start();
        iniciado.set(true);
    }

    @FXML
    private void reanudar(ActionEvent event) {
        servicio.restart();
        parado.set(false);
    }

    @FXML
    private void parar(ActionEvent event) {
        servicio.cancel();
        parado.set(true);
    }

    @FXML
    private void siguiente(ActionEvent event) {
        servicio.setCambiarEstado(true);
        servicio.restart();
    }

    private void reset(ActionEvent event) {
        servicio.restaurarInicio();
       
    }
    
    
}
