/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;
import org.controlsfx.control.CheckComboBox;

/**
 * FXML Controller class
 *
 * @author igugl
 */
public class FXMLAnyadirTutoriaController implements Initializable {
    @FXML
    private Label duracionMinutos;
    @FXML
    private ComboBox<String> dropdownAsignatura;
    ObservableList<Tutoria> tutorias = null;
    ObservableList<Asignatura> asignaturas = null;
    ObservableList<Alumno> alumnos = null;
    @FXML
    private Label horas;
    @FXML
    private Label minutos;
    @FXML
    private Button anyadirButton;
    @FXML
    private CheckComboBox<String> checkComboBox;
    @FXML
    private Label fechaTutoria;
    LocalDate date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tutorias = AccesoBD.getInstance().getTutorias().getTutoriasConcertadas();
        asignaturas = AccesoBD.getInstance().getTutorias().getAsignaturas();
        alumnos = AccesoBD.getInstance().getTutorias().getAlumnosTutorizados();  
        anyadirButton.disableProperty().bind(Bindings.createBooleanBinding(() -> dropdownAsignatura.getSelectionModel().isEmpty(), dropdownAsignatura.valueProperty()).
                or(Bindings.createBooleanBinding(() -> checkComboBox.getCheckModel().getCheckedIndices().size() == 0, checkComboBox.getCheckModel().getCheckedIndices())));

       ObservableList<String> string = FXCollections.observableArrayList();
        for (int i = 0; i < alumnos.size(); i++) {
            string.add(alumnos.get(i).getNombre() + ", " + alumnos.get(i).getApellidos());
        }
        checkComboBox.getItems().setAll(string);
        
 
 // Create the CheckComboBox with the data 
 
 // and listen to the relevant events (e.g. when the selected indices or 
 // selected items change).
 checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
     public void onChanged(ListChangeListener.Change<? extends String> c) {
        checkComboBox.setTitle("Has seleccionado " + checkComboBox.getCheckModel().getCheckedItems().size() + " alumnos");

     }
 });
 
    }    
    
   

    @FXML
    private void sumaDuracion(ActionEvent event) {
        int tiempo = Integer.parseInt(duracionMinutos.getText());
        duracionMinutos.setText(String.valueOf(tiempo + 10));
    }

    @FXML
    private void restaDuracion(ActionEvent event) {
        if(Integer.parseInt(duracionMinutos.getText()) != 10){
            int tiempo = Integer.parseInt(duracionMinutos.getText());
            duracionMinutos.setText(String.valueOf(tiempo - 10));
        }
    }

    private void crearNuevaAsignatura(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLAnyadirAsignatura.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        Stage ventanaPersona = new Stage();
        ventanaPersona.initModality(Modality.APPLICATION_MODAL);
        ventanaPersona.setTitle("Añadir asignatura");
        ventanaPersona.setScene(scene);
        ventanaPersona.showAndWait();
        
        Asignatura asignatura = ((FXMLAddAsignaturaController)loader.getController()).getAsignatura();
        if(asignatura != null){
            asignaturas.add(asignatura);
            AccesoBD.getInstance().salvar();

        }
    }

    private void crearNuevoAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLAnyadirAlumno.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        Stage ventanaPersona = new Stage();
        ventanaPersona.initModality(Modality.APPLICATION_MODAL);
        ventanaPersona.setTitle("Añadir alumno");
        ventanaPersona.setScene(scene);
        ventanaPersona.showAndWait();
        
        Alumno alumno = ((FXMLAddAlumnoController)loader.getController()).getAlumno();
        if(alumno != null){
            alumnos.add(alumno);
            AccesoBD.getInstance().salvar();
        }
    }

    @FXML
    private void sumaHoras(ActionEvent event) {
        int hora = Integer.parseInt(horas.getText());
        if(hora != 19){
            horas.setText(String.valueOf(hora + 1));
        }else{
            horas.setText("8");
        }
    }

    @FXML
    private void restaHoras(ActionEvent event) {
        int hora = Integer.parseInt(horas.getText());
        if(hora != 8){
            horas.setText(String.valueOf(hora - 1));
        }else{
            horas.setText("19");
        }
    }

    @FXML
    private void sumaMinutos(ActionEvent event) {
        int min = Integer.parseInt(minutos.getText());
        if(min != 50){
            minutos.setText(String.valueOf(min + 10));
        }else{
            minutos.setText("00");
        }
    }

    @FXML
    private void restaMinutos(ActionEvent event) {
        int min = Integer.parseInt(minutos.getText());
        if(min != 0){
            minutos.setText(String.valueOf(min - 10));
        }else{
            minutos.setText("50");
        }
    }

    @FXML
    private void anyadirTutoria(ActionEvent event) {
         Tutoria tutoria = new Tutoria();
         Alumno[] alumnos = getAlumnosByIndices(checkComboBox.getCheckModel().getCheckedIndices());
         tutoria.getAlumnos().addAll(alumnos);
         tutoria.setEstado(Tutoria.EstadoTutoria.PEDIDA);
         Duration duracion = Duration.ofMinutes(Integer.parseInt(duracionMinutos.getText()));
         tutoria.setDuracion(duracion);
         tutoria.setFecha(date);
         LocalTime time = LocalTime.of(Integer.parseInt(horas.getText()), Integer.parseInt(minutos.getText()));
         tutoria.setInicio(time);
         Asignatura asignatura = null;
         for(int i = 0; i < asignaturas.size(); i++){
             if(asignaturas.get(i).getCodigo().equals(dropdownAsignatura.getValue())){
                 asignatura = asignaturas.get(i);
             }
         }
         tutoria.setAsignatura(asignatura);
         //check si se solapan tutorias
         if(solapanTutorias(tutoria)){
             //lanzar mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setTitle("No se puede añadir la tutoria"); 
            alert.setHeaderText("La tutoria se solapa con otras tutorias"); 
            alert.setContentText("La tutoria no puede ser añadida porque se solapa con alguna de las tutorias existentes"); 
            alert.showAndWait();
         }else if(tutoria.getInicio().plusMinutes(tutoria.getDuracion().toMinutes()).compareTo(LocalTime.of(20, 0)) > 0){
             Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setTitle("Tutoría fuera del horario"); 
            alert.setHeaderText("No es posible añadir la tutoría"); 
            alert.setContentText("La tutoria no puede ser añadida porque sobrepasa el límite horario de las 20h"); 
            alert.showAndWait();
         }
         else{ //si no se solaan
            tutorias.add(tutoria);
            AccesoBD.getInstance().salvar();
            ((Stage)duracionMinutos.getScene().getWindow()).close(); 

         }
    }

    private boolean solapanTutorias(Tutoria tutoria) {
       boolean res = false;
       for(int i = 0; i < tutorias.size() && res == false; i++){
           Tutoria tutoriaGuardada = tutorias.get(i);
           // si las fechas son iguales mirar si se solapan
           if(tutoriaGuardada.getFecha().equals(tutoria.getFecha())){
               LocalTime inicioNueva = tutoria.getInicio();
               LocalTime finalNueva = inicioNueva.plusMinutes(tutoria.getDuracion().toMinutes());
               LocalTime inicioTutoriaExistente = tutoriaGuardada.getInicio();
               LocalTime finalTutoriaExistente = inicioTutoriaExistente.plusMinutes(tutoriaGuardada.getDuracion().toMinutes());
               //A.end >= B.start AND A.start <= B.end
               boolean overlap = finalNueva.compareTo(inicioTutoriaExistente) >= 0 && inicioNueva.compareTo(finalTutoriaExistente) <= 0;
               if(overlap){
                   res = true;
               }
           }
       }
       return res;
    }
    
    private Alumno[] getAlumnosByIndices(ObservableList<Integer> array){
        Alumno[] res = new Alumno[array.size()];
        for(int i = 0; i < array.size(); i++){
            Alumno alumno = alumnos.get(array.get(i));
            res[i] = alumno;
        }
       return res;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage)duracionMinutos.getScene().getWindow()).close();   
    }

    @FXML
    private void dropdownAsignatura(Event event) {
        //se puede evitar hacer bucles innecesarios haciendo toString en cada clase, pero como no hay.....
        ObservableList<String> codigos = FXCollections.observableArrayList(); 
        for(int i = 0; i < asignaturas.size(); i++){
            codigos.add(asignaturas.get(i).getCodigo());
        }
        dropdownAsignatura.setItems(codigos);
    }
    
    
    public void setDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        this.date = date;
        String formattedString = date.format(formatter);
        fechaTutoria.setText(formattedString);
    }


}