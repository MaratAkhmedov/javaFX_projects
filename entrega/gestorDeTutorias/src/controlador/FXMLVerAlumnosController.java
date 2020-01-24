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
import modelo.Alumno;

/**
 * FXML Controller class
 *
 * @author cosmi
 */
public class FXMLVerAlumnosController implements Initializable {
//Lo de abajo no valdría ahora para nada
    @FXML private TableView<Alumno> alumnTable;
    @FXML private TableColumn<Alumno, String> alumnName; 
    @FXML private TableColumn<Alumno, String> alumnSurname;
    @FXML private TableColumn<Alumno, String> alumnEmail; 
    ObservableList<Alumno> verAlumn = AccesoBD.getInstance().getTutorias().getAlumnosTutorizados();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        alumnName.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        alumnSurname.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellidos"));
        alumnEmail.setCellValueFactory(new PropertyValueFactory<Alumno, String>("email"));
        
        alumnTable.setItems(verAlumn);
        }

}
