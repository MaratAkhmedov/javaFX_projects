/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Sesion;

/**
 * FXML Controller class
 *
 * @author igugl
 */
public class FXMLSettingsViewController implements Initializable {
    @FXML
    private Spinner<Integer> descansoEntreCircuitos;
    @FXML
    private Spinner<Integer> numEjercicios;
    @FXML
    private Spinner<Integer> durEjercicio;
    @FXML
    private Spinner<Integer> descansoEntreEjercicios;
    @FXML
    private Spinner<Integer> numCircuitos;
    @FXML
    private Button startButton;
    
    
    private Sesion sesion = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        configurarSpinners();
    }    

    @FXML
    private void startButton(ActionEvent event) throws IOException {
       
        sesion = new Sesion();
        sesion.setN_circuitos(numCircuitos.getValue());
        sesion.setDescanso_circuito(Duration.ofSeconds(descansoEntreCircuitos.getValue()));
        sesion.setN_ejercicios(numEjercicios.getValue());
        sesion.setT_ejercicio(Duration.ofSeconds(durEjercicio.getValue()));
        sesion.setDescanso_ejercicio(Duration.ofSeconds(descansoEntreEjercicios.getValue()));
        
        /*
        //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLTemporizador.fxml"));
            Parent root = loader.load();
             
            //Get controller of scene2
            FXMLTemporizadorController controller = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
           
 
          
            
            
        //Show scene 2 in new window            
            Scene tableViewScene = new Scene(root);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        controller.initSesion(sesion);
        window.show();
        
        */
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vista/FXMLTemporizador.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        //access the controller and call a method
        FXMLTemporizadorController controller = loader.getController();
        controller.initSesion(sesion);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void changeScreenButtonPushed(ActionEvent event) throws IOException {
         Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vista/FXMLTemporizador.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
     private void configurarSpinners() {
        // NUMEROS CIRCUITOS 
        // Value factory.
        SpinnerValueFactory<Integer> numCircuitosValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        numCircuitos.setValueFactory(numCircuitosValue);
        
        //CONFIGURACION SPINNERS DESCANSO ENTRE CIRCUITOS
        SpinnerValueFactory.IntegerSpinnerValueFactory descansoCircuitosConfig = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 10);
        descansoCircuitosConfig.setAmountToStepBy(5);
        SpinnerValueFactory<Integer> descansoCircuitos = descansoCircuitosConfig;
        descansoEntreCircuitos.setValueFactory(descansoCircuitos);
        
        //CONFIGURACION SPINNERS numEjercicios
        SpinnerValueFactory<Integer> numeroEjerciciosValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        numEjercicios.setValueFactory(numeroEjerciciosValue);
        
         //CONFIGURACION SPINNERS duracion de ejercicios
        SpinnerValueFactory.IntegerSpinnerValueFactory duracionEjerciciosConfig = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 5);
        duracionEjerciciosConfig.setAmountToStepBy(5);
        SpinnerValueFactory<Integer> duracionEjercicios = duracionEjerciciosConfig;
        durEjercicio.setValueFactory(duracionEjercicios);
        
         
         //CONFIGURACION SPINNERS duracion de ejercicios
        SpinnerValueFactory.IntegerSpinnerValueFactory  configDescansoEjercicios= new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300, 10);
        configDescansoEjercicios.setAmountToStepBy(5);
        SpinnerValueFactory<Integer> descansoEjerciciosValue = configDescansoEjercicios;
        
        descansoEntreEjercicios.setValueFactory(descansoEjerciciosValue);
    }
     
     public Sesion getSesion(){
         return sesion;
     }
     
}
