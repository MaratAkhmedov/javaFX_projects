/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class FXMLPersonasTableController implements Initializable {

    /**
     * Probar hacer todo eso pero con una única escena
     * ventanaPersona.setScene(scene);
     */
    private ObservableList<Persona> datos = null; // Colecci�n vinculada a la vista.
    
    @FXML
    private Button addButton;
    @FXML
    private Button modButton;
    @FXML
    private Button delButton;
    @FXML
    private TableView<Persona> personasTable;
    @FXML
    private TableColumn<Persona, String> nomColum;
    @FXML
    private TableColumn<Persona, String> apellColum;

    
    private void inicializaModelo() {
        ArrayList<Persona> misdatos = new ArrayList<Persona>();
        misdatos.add(new Persona("Pepe", "García"));
        misdatos.add(new Persona("María", "Pérez"));
        datos = FXCollections.observableArrayList(misdatos);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        inicializaModelo();
        
        personasTable.setItems(datos);
        nomColum.setCellValueFactory(celData->celData.getValue().NombreProperty());
        apellColum.setCellValueFactory(celData->celData.getValue().ApellidosProperty());        
        delButton.disableProperty().bind(Bindings.not(personasTable.focusedProperty()));
        modButton.disableProperty().bind(Bindings.not(personasTable.focusedProperty()));
        
    }    

    @FXML
    private void addPersona(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLPersona.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLPersona.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        Stage ventanaPersona = new Stage();
        ventanaPersona.initModality(Modality.APPLICATION_MODAL);
        ventanaPersona.setTitle("Añadir Persona");
        ventanaPersona.setScene(scene);
        //ventanaPersona.show();
        ventanaPersona.showAndWait();
        
        Persona persona = ((FXMLPersonaController)loader.getController()).getPersona();
        if(persona != null){
            datos.add(persona);
        }
    }

    @FXML
    private void modifyPersona(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLPersona.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLPersona.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        Stage ventanaPersona = new Stage();
        ventanaPersona.initModality(Modality.APPLICATION_MODAL);
        ventanaPersona.setTitle("Modificar Persona");
        ventanaPersona.setScene(scene);
        //ventanaPersona.show();
        Persona p = personasTable.getSelectionModel().getSelectedItem();
        ((FXMLPersonaController)loader.getController()).setPerson(p);
        ventanaPersona.showAndWait();
        
    }

    @FXML
    private void borrarPersona(ActionEvent event) {
        datos.remove(personasTable.getSelectionModel().getSelectedItem());
    }
    
}
