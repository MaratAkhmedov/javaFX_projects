/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import modelo.Tutoria;
import modelo.Tutoria.EstadoTutoria;

/**
 * FXML Controller class
 *
 * @author igugl
 */
public class FXMLModifyTutoriaController implements Initializable {
    @FXML
    private ComboBox<EstadoTutoria> dropdownEstados;
    @FXML
    private TextArea textArea;
    @FXML
    private Button modificar;
     ArrayList<EstadoTutoria> estados; 
    public Tutoria tutoria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estados = new ArrayList<EstadoTutoria>();
        estados.add(EstadoTutoria.ANULADA);
        estados.add(EstadoTutoria.NO_ASISTIDA);
        estados.add(EstadoTutoria.PEDIDA);
        estados.add(EstadoTutoria.REALIZADA);
        ObservableList<EstadoTutoria> lista = FXCollections.observableArrayList(estados);
        dropdownEstados.setItems(lista);
    }    

    @FXML
    private void modificar(ActionEvent event) {
        tutoria.setAnotaciones(textArea.getText());
        tutoria.setEstado(dropdownEstados.getValue());
        AccesoBD.getInstance().salvar();
        ((Stage)textArea.getScene().getWindow()).close();        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage)textArea.getScene().getWindow()).close();
    }
    
    public void setTextArea(String text){
        textArea.setText(text);
    }
    
    public void setComboBox(EstadoTutoria Estado){
        dropdownEstados.setValue(Estado);
    }
    
    public void setTutoria(Tutoria tut){
        tutoria = tut;
    }
}
