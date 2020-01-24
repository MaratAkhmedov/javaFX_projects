package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;

import modelo.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VistaListaControlador implements Initializable {

    private ObservableList<Persona> datos = null; // Colecci�n vinculada a la vista.


    @FXML
    private TextField nombreTF;
    @FXML
    private TextField apellidoTF;
    @FXML
    private Button addB;
    @FXML
    private Button borrarB;
    @FXML
    private ListView<Persona> listaLV;

    @FXML
    void addAccion(ActionEvent event) throws IOException {
        /*--------------------------------------------------------------------*/
        /* anadir aqui el codigo para mostrar la nueva ventana*/
        FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/vista/FXMLPersona.fxml"));
        Parent root = miLoader.load();
//          Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLPersona.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir persona");
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLPersonaController controller = miLoader.getController();
        stage.showAndWait();
        
        if(controller.hayPersona()){
            datos.add(controller.getPersona());
        }
      
        
 

       /*------------------------------------------------------------------------*/
       /*comentar este codigo hasta final del metodo al añadir la nueva ventana  */
       /*este codigo debera de estar el la clase controladora de la nueva ventana*/
       
        // añade a la colección si los campos no son vacíos y no contienen únicamente blancos
//        if ((!nombreTF.getText().isEmpty())
//                && (nombreTF.getText().trim().length() != 0)
//                && (!apellidoTF.getText().isEmpty())
//                && (apellidoTF.getText().trim().length() != 0)) {
//            datos.add(new Persona(nombreTF.getText(), apellidoTF.getText()));
//            nombreTF.clear();
//            apellidoTF.clear();
//            nombreTF.requestFocus();  //cambio del foco al textfield.
//
//        }
    }

    @FXML
    void borrarAccion(ActionEvent event) {
        /*int i = listaLV.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            datos.remove(i);
        }*/
        datos.remove(listaLV.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        ArrayList<Persona> misdatos = new ArrayList<Persona>();
        misdatos.add(new Persona("Pepe", "García"));
        misdatos.add(new Persona("María", "Pérez"));
        /*----------------------------------------------------------------*/
        /*  crear la listaobservable datos a partir del arraylist misdatos*/
        datos = FXCollections.observableArrayList(misdatos);
        
        /*----------------------------------------------------------------*/
        /*  sociar la listaobservable datos al listview listaLV           */   
        listaLV.setItems(datos);
        
        /*-------------------------------------------------------*/
        /* asignar aqui el nuevo estilo de la celda*/
        listaLV.setCellFactory(c->new micelda());
        
        
        /*-------------------------------------------------------*/
        // inhabilitar botones al arrancar.
//        addB.setDisable(true);
        borrarB.setDisable(true);
//        //binding para habilitar el boton Añadir
//        addB.disableProperty().bind(nombreTF.textProperty().isEmpty());
//        // binding para el boton borrar
        borrarB.disableProperty().bind(Bindings.not(listaLV.focusedProperty()));
    }

}
/*-------------------------------------------------------*/
/* crear aqui la nueva clase que extiende a ListCell     */
 
class micelda extends ListCell<Persona>{

    @Override
    protected void updateItem(Persona item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if((item == null) || empty){
            setText(null);
        }else{
            setText(item.getApellidos() + ", " + item.getNombre());
        }
    }
    

}



/*-------------------------------------------------------*/
