/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Asignatura;

/**
 * FXML Controller class
 *
 * @author cosmi
 */
public class FXMLVerAsignaturasController implements Initializable {
    
    @FXML private TableView<Asignatura> asigTable;
    @FXML private TableColumn<Asignatura, String> asigCode; 
    @FXML private TableColumn<Asignatura, String> asigDescription;
    ObservableList<Asignatura> verAsig = AccesoBD.getInstance().getTutorias().getAsignaturas();
    ObservableList<Asignatura> verTFGyTFM = null;
    /**
     * Initializes the controller class.
     */
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        asigCode.setCellValueFactory(new PropertyValueFactory<Asignatura, String>("codigo"));
        asigDescription.setCellValueFactory(new PropertyValueFactory<Asignatura, String>("descripcion"));
        
       
        
        asigTable.setItems(verAsig);
    
}

}