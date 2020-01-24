/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;

/**
 * FXML Controller class
 *
 * @author igugl
 */
public class FXMLTutoriaInfoEstadoController implements Initializable {
    @FXML
    private ListView<String> AlumnosList;
    @FXML
    private Label duracionMinutos;
    @FXML
    private Label fechaTutoria;
    @FXML
    private Label nombreAsignatura;
    public Tutoria tutoria;
    @FXML
    private Label hora;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hora.setDisable(false);
    }    


    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage)nombreAsignatura.getScene().getWindow()).close();
    }

    private void anularTutoria(ActionEvent event) {
        ((Stage)nombreAsignatura.getScene().getWindow()).close();
    }
    
    
    public void setNombreAsignatura(Asignatura asignatura){
        nombreAsignatura.setText(asignatura.getCodigo());
    }
    
    public void setFecha(LocalDate fecha){
        fechaTutoria.setText(fecha.toString());
    }
    
    public void setHora(LocalTime time){
        hora.setText(String.valueOf(time.toString()));
    }
   
    public void setDuracion(Duration duracion){
        duracionMinutos.setText(String.valueOf(duracion.toMinutes()));
    }
    
    public void setAlumnos(ObservableList<Alumno> alumnos){
        ArrayList<String> alumn = new ArrayList<String>();
        for(int i = 0; i < alumnos.size(); i++){
            alumn.add(alumnos.get(i).getNombre() + ", " + alumnos.get(i).getApellidos());
        }
        ObservableList<String> res = FXCollections.observableArrayList(alumn);

        AlumnosList.setItems(res);
    }
    
   
}
