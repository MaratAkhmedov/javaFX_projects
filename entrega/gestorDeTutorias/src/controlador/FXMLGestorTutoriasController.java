/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import accesoBD.AccesoBD;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;
import modelo.Tutoria.EstadoTutoria;
import modelo.Tutorias;

/**
 *
 * @author igugl
 */
public class FXMLGestorTutoriasController implements Initializable {    
    private HBox HBoxPicker;    
    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox HBox;
    @FXML
    private VBox VBoxPicker;
    private ObservableList<Tutoria> listaObservabletutoriasDiaConcreto;
    @FXML
    private Button anyadirTutoria;
    @FXML
    private MenuItem borrarAsignaturaItem;
    @FXML
    private MenuItem anyadirAsignaturaItem;
    @FXML
    private MenuItem verAsignaturaItem;
    @FXML
    private MenuItem borrarAlumnoItem;
    @FXML
    private MenuItem anyadirAlumnoItem;
    @FXML
    private MenuItem verAlumnoItem;
    Tutorias misTutorias;
    ObservableList<Tutoria> tutorias = null;
    ObservableList<Asignatura> asignaturas = null;
    ObservableList<Alumno> alumnos = null;
    private LocalDate selectedDate = null;
    DatePicker picker;
    @FXML
    private TableView<Tutoria> tutoriasTable;
    @FXML
    private TableColumn<Tutoria, String> asignaturaColumn;
    @FXML
    private TableColumn<Tutoria, LocalTime> horaInicio;
    @FXML
    private TableColumn<Tutoria, EstadoTutoria> estadoColumn;
    @FXML
    private TableColumn<Tutoria, Tutoria> InfoButtonColumn;
    @FXML
    private TableColumn<Tutoria, Tutoria> modifyButtonColumn;
    @FXML
    private TableColumn<Tutoria, String> duracionColumn;

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        tutorias = AccesoBD.getInstance().getTutorias().getTutoriasConcertadas();
        asignaturas = AccesoBD.getInstance().getTutorias().getAsignaturas();
        alumnos = AccesoBD.getInstance().getTutorias().getAlumnosTutorizados();        
        
        picker = new DatePicker(LocalDate.now());
        
        picker.setShowWeekNumbers(false);
        final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           for(int i = 0; i < tutorias.size(); i++){
                                if(tutorias.get(i).getFecha().equals(item)){
                                    setStyle("-fx-background-color: #36e506");
                                }
                                //marcar tutoria como realizada
                                if(LocalDate.now().compareTo(tutorias.get(i).getFecha()) > 0){
                                    
                                }
                            }
                            if (item.isBefore(LocalDate.now())) {
                                    setStyle("-fx-background-color: #cfcece;");
                            }
                            if(item.getDayOfWeek().toString() == "SATURDAY" || item.getDayOfWeek().toString() == "SUNDAY"){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #fa9898;");
                            }
                            
                        }
                };
            }
        };
        picker.setDayCellFactory(dayCellFactory);
        picker.setPadding(new Insets(20,20,20,20));
        anyadirTutoria.disableProperty().bind(Bindings.createBooleanBinding(() -> picker.getValue().compareTo(LocalDate.now())<0, picker.valueProperty()));
        DatePickerSkin datePickerSkin = new DatePickerSkin(picker);
        Node popupContent = datePickerSkin.getPopupContent(); 
        
        //si es festivo cambiamos focus para seleccionar un dia no festivo
        if(picker.getValue().getDayOfWeek().toString() == "SATURDAY"){
            picker.setValue(picker.getValue().plusDays(2));
        }
        if(picker.getValue().getDayOfWeek().toString() == "SUNDAY"){
            picker.setValue(picker.getValue().plusDays(1));
        }

        for (Node node : popupContent.lookupAll(".day-cell")) {
            node.setOnMouseClicked((event) -> {
                DateCell dateCell = (DateCell) node;
                setMiListaTutorias(listaObservabletutoriasDiaConcreto, picker.getValue());
            });
        }   
        VBoxPicker.getChildren().add(popupContent);
        
        setMiListaTutorias(listaObservabletutoriasDiaConcreto, picker.getValue());

        
     
        
        
        InfoButtonColumn.setCellValueFactory(
    param -> new ReadOnlyObjectWrapper<>(param.getValue())
);
InfoButtonColumn.setCellFactory(param -> new TableCell<Tutoria, Tutoria>() {
    private final Button infoButton = new Button("Más información");

    @Override
    protected void updateItem(Tutoria tutoriaSeleccionada, boolean empty) {
        super.updateItem(tutoriaSeleccionada, empty);

        if (tutoriaSeleccionada == null) {
            setGraphic(null);
            return;
        }

        setGraphic(infoButton);
        infoButton.setOnAction(
                (event) -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLTutoriaInfoEstado.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {

                    }
                     FXMLTutoriaInfoEstadoController controlador = loader.getController();
                    controlador.setFecha(tutoriaSeleccionada.getFecha());
                    controlador.setNombreAsignatura(tutoriaSeleccionada.getAsignatura());
                    controlador.setHora(tutoriaSeleccionada.getInicio());
                    controlador.setAlumnos(tutoriaSeleccionada.getAlumnos());
                    controlador.setDuracion(tutoriaSeleccionada.getDuracion());
                    Scene scene = new Scene(root);
                    Stage ventanaPersona = new Stage();
                    ventanaPersona.initModality(Modality.APPLICATION_MODAL);
                    ventanaPersona.setTitle("Información Tutoria");
                    ventanaPersona.setScene(scene);
                    ventanaPersona.showAndWait();
                }
        );
    }
});


modifyButtonColumn.setCellValueFactory(
    param -> new ReadOnlyObjectWrapper<>(param.getValue())
);
modifyButtonColumn.setCellFactory(param -> new TableCell<Tutoria, Tutoria>() {
    private final Button modify = new Button("Modificar");
    @Override
    protected void updateItem(Tutoria tutoriaSeleccionada, boolean empty) {
        super.updateItem(tutoriaSeleccionada, empty);

        if (tutoriaSeleccionada == null) {
            setGraphic(null);
            return;
        }

        setGraphic(modify);
        modify.setOnAction(

                (event) -> {
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLModifyTutoria.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                       
                    }
                     FXMLModifyTutoriaController controlador = loader.getController();
                     controlador.setTextArea(tutoriaSeleccionada.getAnotaciones());
                     controlador.setComboBox(tutoriaSeleccionada.getEstado());
                     controlador.setTutoria(tutoriaSeleccionada);
                    
                    Scene scene = new Scene(root);
                    Stage ventanaPersona = new Stage();
                    ventanaPersona.initModality(Modality.APPLICATION_MODAL);
                    ventanaPersona.setTitle("Información Tutoria");
                    ventanaPersona.setScene(scene);
                    ventanaPersona.showAndWait();
                }
        );
    }
});
    }    
    /**
     * 
     * @param date la fecha para la que quieres obtener las tutorias concertadas
     * @param miListaTutorias es la lista que nos devuelva con las tutorias del dia concreto
     * @return lista observable con las tutorias de un dia especifico
     */
    public ObservableList<Tutoria> setMiListaTutorias(ObservableList<Tutoria> miListaTutorias ,LocalDate date){
        ArrayList<Tutoria> tutoriasDiaSeleccionado = new ArrayList<Tutoria>();
        for(int i = 0; i < tutorias.size(); i++){
            if(tutorias.get(i).getFecha().equals(date)){
                tutoriasDiaSeleccionado.add(tutorias.get(i));
            }
        }
        miListaTutorias = FXCollections.observableArrayList(tutoriasDiaSeleccionado);
        tutoriasTable.setItems(miListaTutorias);
        asignaturaColumn.setCellValueFactory(celData->celData.getValue().getAsignatura().codigoProperty());
        duracionColumn.setCellValueFactory(celData->celData.getValue().duracionProperty().asString(celData.getValue().getDuracion().toMinutes() + " minutos"));
        horaInicio.setCellValueFactory(celData->celData.getValue().inicioProperty());
        estadoColumn.setCellValueFactory(celData->celData.getValue().estadoProperty());
        return miListaTutorias;
    }

    @FXML
    private void anyadirTutoria(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLAnyadirTutoria.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage ventanaPersona = new Stage();
        ventanaPersona.initModality(Modality.APPLICATION_MODAL);
        ventanaPersona.setTitle("Añadir Tutoria");
        ventanaPersona.setScene(scene);
        ((FXMLAnyadirTutoriaController)loader.getController()).setDate(picker.getValue());
        ventanaPersona.showAndWait();
        setMiListaTutorias(listaObservabletutoriasDiaConcreto, picker.getValue());
    }

    @FXML
    private void borrarAsignatura(ActionEvent event) {
        List<String> listaCodigos = new ArrayList<String>();
        for(int i = 0; i<asignaturas.size(); i++){
            listaCodigos.add(asignaturas.get(i).getCodigo());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Selecciona la asignatura a borrar", listaCodigos); 
        dialog.setTitle("Borrar asignatura"); 
        dialog.setHeaderText("¿Qué Asignatura quieres borrar?"); 
        dialog.setContentText("Elige:"); 
        Optional<String> result = dialog.showAndWait(); 
        // Pre Java 8 
        if (result.isPresent()) { 
            for(int i = 0; i < asignaturas.size(); i++){
                if(asignaturas.get(i).codigoProperty().get().equals(result.get())){
                    asignaturas.remove(i);
                }
            }
            AccesoBD.getInstance().salvar();
            
        }
    }

    @FXML
    private void anyadirAsignatura(ActionEvent event) throws IOException {
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

    @FXML
    private void verAsignaturas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLVerAsignaturas.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage ventanaAsignatura = new Stage();
        ventanaAsignatura.setScene(scene);
        ventanaAsignatura.initModality(Modality.APPLICATION_MODAL);
        ventanaAsignatura.setTitle("Lista de Asignaturas");
        ventanaAsignatura.showAndWait();
    }

    @FXML
    private void borrarAlumno(ActionEvent event) {
        List<String> lista = new ArrayList<String>();
        for(int i = 0; i<alumnos.size(); i++){
            lista.add(alumnos.get(i).getNombre() + ", " +  alumnos.get(i).getApellidos());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Selecciona el alumno a borrar", lista); 
        dialog.setTitle("Borrar alumno"); 
        dialog.setHeaderText("¿Qué Alumno quieres borrar?"); 
        dialog.setContentText("Elige:"); 
        Optional<String> result = dialog.showAndWait(); 
        // Pre Java 8 
        if (result.isPresent()) { 
            for(int i = 0; i < alumnos.size(); i++){
                String alumno = alumnos.get(i).getNombre() + ", " +  alumnos.get(i).getApellidos();
                String resultado = result.get();
                if(alumno.equals(resultado)){
                    alumnos.remove(i);
                }
            }
            AccesoBD.getInstance().salvar();
        }
    }

    @FXML
    private void anyadirAlumno(ActionEvent event) throws IOException {
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
    private void verAlumnos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLVerAlumnos.fxml")); 
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage ventanaAlumno = new Stage();
        ventanaAlumno.initModality(Modality.APPLICATION_MODAL);
        ventanaAlumno.setScene(scene);
        ventanaAlumno.setTitle("Lista de Alumnos");
        ventanaAlumno.showAndWait();

    }

    
    public LocalDate getSelectedDate(){
        return selectedDate;
    }
   

    
}
